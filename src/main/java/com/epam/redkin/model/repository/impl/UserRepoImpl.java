package com.epam.redkin.model.repository.impl;

import com.epam.redkin.model.connectionpool.DBManager;
import com.epam.redkin.model.entity.Role;
import com.epam.redkin.model.entity.User;
import com.epam.redkin.model.repository.UserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.epam.redkin.model.repository.impl.Constants.*;

public class UserRepoImpl implements UserRepo {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserRepoImpl.class.getName());


    @Override
    public int create(User user) {
        int key = -1;
        try (Connection connection = DBManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(ADD_USER, Statement.RETURN_GENERATED_KEYS)) {
            setData(user, statement);
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                key = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return key;
    }


    @Override
    public User read(int id) {
        User user = new User();
        try (Connection connection = DBManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_USER_BY_ID)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                user = extractUser(rs);
            }
        } catch (SQLException | NullPointerException e) {
            LOGGER.error(e.getMessage());
            throw new RuntimeException("Can`t read user. ID = " + id, e);
        }
        return user;
    }

    @Override
    public boolean update(User user) {
        try (Connection connection = DBManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_USER)) {
            setData(user, statement);
            statement.setInt(9, user.getUserId());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(int id) {
        try (Connection connection = DBManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_USER)) {
            statement.setInt(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User getByEmail(String email) {
        User user = null;
        try (Connection connection = DBManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_USER_BY_EMAIL)) {
            statement.setString(1, email);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                user = extractUser(rs);
            }
        } catch (SQLException | NullPointerException e) {
            LOGGER.error(e.getMessage());
            throw new RuntimeException("Can`t read user. Email = " + email, e);
        }
        return user;
    }


    @Override
    public boolean checkUser(String email) {
        try (Connection connection = DBManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_USER_BY_EMAIL)) {
            statement.setString(1, email);
            return statement.executeQuery().next();
        } catch (SQLException | NullPointerException e) {
            LOGGER.error(String.valueOf(e));
            throw new RuntimeException("User is not valid. Email = " + email, e);
        }
    }

    @Override
    public List<User> getUsersByRole(String role) {
        List<User> users = new ArrayList<>();
        try (Connection connection = DBManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_USERS_BY_ROLE)) {
            statement.setString(1, role.toLowerCase());
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                User user = new User();
                extractUser(rs);
                users.add(user);
            }
        } catch (SQLException | NullPointerException e) {
            LOGGER.error(String.valueOf(e));
            throw new RuntimeException("Cannot extract data. Role = " + role.toUpperCase(), e);
        }
        return users;
    }

    @Override
    public void updateBlocked(int id, boolean status) {
        try (Connection connection = DBManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_BLOCKED)) {
            statement.setBoolean(1, status);
            statement.setInt(2, id);
            statement.executeUpdate();
        } catch (SQLException | NullPointerException e) {
            LOGGER.error(String.valueOf(e));
            throw new RuntimeException("Cannot change status = " + status + " with id = " + id, e);
        }
    }

    private User extractUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setUserId(rs.getInt("id"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));
        user.setFirstName(rs.getString("first_name"));
        user.setLastName((rs.getString("last_name")));
        user.setPhone(rs.getString("phone"));
        user.setBirthDate(rs.getObject("birth_date", LocalDate.class));
        user.setRole(Role.valueOf(rs.getString("role").toUpperCase()));
        user.setBlocked(rs.getBoolean("blocked"));
        return user;
    }


    private void setData(User user, PreparedStatement statement) throws SQLException {
        statement.setString(1, user.getEmail());
        statement.setString(2, user.getPassword());
        statement.setString(3, user.getFirstName());
        statement.setString(4, user.getLastName());
        statement.setString(5, user.getPhone());
        statement.setObject(6, user.getBirthDate());
        statement.setString(7, user.getRole().getName());
        statement.setBoolean(8, user.isBlocked());
    }

}
