package com.epam.redkin.model.repository.impl;

import com.epam.redkin.model.connectionpool.ConnectionPools;
import com.epam.redkin.model.entity.Role;
import com.epam.redkin.model.entity.User;
import com.epam.redkin.model.exception.DataBaseException;
import com.epam.redkin.model.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.epam.redkin.model.repository.impl.Constants.*;

public class UserRepositoryImpl implements UserRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserRepositoryImpl.class.getName());
    private final DataSource dataSource;

    public UserRepositoryImpl() {
        dataSource = ConnectionPools.getProcessing();
    }

    public UserRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public int create(User user) {
        int key = -1;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(ADD_USER, Statement.RETURN_GENERATED_KEYS)) {
            setStatement(user, statement);
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                key = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DataBaseException("Cannot add user into database, user = " + user);
        }
        return key;
    }


    @Override
    public User read(int id) {
        User user = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_USER_BY_ID)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                user = extractUser(rs);
            }
        } catch (SQLException | NullPointerException e) {
            LOGGER.error(e.getMessage());
            throw new DataBaseException("Cannot read user from database, user_id = " + id);
        }
        return user;
    }

    @Override
    public boolean update(User user) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_USER)) {
            setStatement(user, statement);
            statement.setInt(10, user.getUserId());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DataBaseException("Cannot update user, user = " + user);
        }
    }

    @Override
    public boolean delete(int id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_USER)) {
            statement.setInt(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DataBaseException("Cannot delete user with id = " + id);
        }
    }

    @Override
    public User getUserByEmail(String email) {
        User user = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_USER_BY_EMAIL)) {
            statement.setString(1, email);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                user = extractUser(rs);
            }
        } catch (SQLException | NullPointerException e) {
            LOGGER.error(e.getMessage());
            throw new DataBaseException("Cannot extract user with email = " + email);
        }
        return user;
    }


    @Override
    public boolean checkUserByEmail(String email) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_USER_BY_EMAIL)) {
            statement.setString(1, email);
            return statement.executeQuery().next();
        } catch (SQLException | NullPointerException e) {
            LOGGER.error(e.getMessage());
            throw new DataBaseException("User is not valid with email = " + email);
        }
    }

    @Override
    public List<User> getUsersByRole(String role) {
        List<User> users = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_USERS_BY_ROLE)) {
            statement.setString(1, role.toLowerCase());
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                users.add(extractUser(rs));
            }
        } catch (SQLException | NullPointerException e) {
            LOGGER.error(e.getMessage());
            throw new DataBaseException("Cannot extract list of users by role = " + role);
        }
        return users;
    }

    @Override
    public void updateBlocked(int id, boolean status) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_BLOCKED)) {
            statement.setBoolean(1, status);
            statement.setInt(2, id);
            statement.executeUpdate();
        } catch (SQLException | NullPointerException e) {
            LOGGER.error(e.getMessage());
            throw new DataBaseException("Cannot change status = " + status + " with id = " + id);
        }
    }

    @Override
    public void updateRememberUserToken(int id, String token) {
        try (Connection connection = ConnectionPools.getProcessing().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER_LOG_IN_TOKEN_BY_ID)) {
            preparedStatement.setString(1, token);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException | DataBaseException e) {
            LOGGER.warn(String.format("User id: %d, token %s update error", id, token), e);
            throw new DataBaseException("service.commonError", e);
        }
    }

    @Override
    public User findUserByIdAndToken(int userId, String token) {
        ResultSet resultSet = null;
        try (Connection connection = ConnectionPools.getProcessing().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_USER_BY_ID_AND_TOKEN)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setString(2, token);
            resultSet = preparedStatement.executeQuery();
            return extractUser(resultSet);
        } catch (SQLException | DataBaseException e) {
            LOGGER.warn(String.format("User userId: %d, token %s finding error", userId, token), e);
            throw new DataBaseException("service.commonError", e);
        } finally {
            closeResultSet(resultSet);
        }
    }

    @Override
    public void deleteRememberUserToken(int userId) {
        try(Connection connection = ConnectionPools.getProcessing().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER_LOG_IN_TOKEN_TO_NULL)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.executeUpdate();
        } catch (SQLException | DataBaseException e) {
            LOGGER.warn(String.format("User id: %d, token delete error", userId), e);
            throw new DataBaseException("service.commonError", e);
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
        user.setToken(rs.getString("log_in_token"));
        return user;
    }


    private void setStatement(User user, PreparedStatement statement) throws SQLException {
        statement.setString(1, user.getEmail());
        statement.setString(2, user.getPassword());
        statement.setString(3, user.getFirstName());
        statement.setString(4, user.getLastName());
        statement.setString(5, user.getPhone());
        statement.setObject(6, user.getBirthDate());
        statement.setString(7, user.getRole().getName());
        statement.setBoolean(8, user.isBlocked());
        statement.setString(9, user.getToken());
    }

    protected void closeResultSet(ResultSet resultSet) throws DataBaseException {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                LOGGER.error("Can`t close resultSet", e);
                throw new DataBaseException("service.commonError", e);
            }
        }
    }

}
