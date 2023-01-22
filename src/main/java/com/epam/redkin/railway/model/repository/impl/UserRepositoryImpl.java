package com.epam.redkin.railway.model.repository.impl;

import com.epam.redkin.railway.model.builder.UserBuilder;
import com.epam.redkin.railway.model.entity.Role;
import com.epam.redkin.railway.model.entity.User;
import com.epam.redkin.railway.model.exception.DAOException;
import com.epam.redkin.railway.util.constants.AppContextConstant;
import com.epam.redkin.railway.model.repository.UserRepository;
import org.apache.commons.dbutils.DbUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserRepositoryImpl implements UserRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserRepositoryImpl.class);
    private final DataSource dataSource;

    public UserRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public int create(User user) throws SQLException {
        LOGGER.info("Started the method create. User: " + user);
        int key = -1;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);
            LOGGER.info("Transaction started");
            statement = connection.prepareStatement(Constants.ADD_USER, Statement.RETURN_GENERATED_KEYS);
            setStatement(user, statement);
            statement.executeUpdate();
            connection.commit();
            LOGGER.info("Transaction done");
            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                key = resultSet.getInt(1);
            }
        } catch (SQLException | NullPointerException e) {
            assert connection != null;
            connection.rollback();
            LOGGER.error(e.getClass() + " in method create: " + e);
            throw new DAOException("Cannot add user into database, user = " + user, e);
        } finally {
            assert connection != null;
            connection.setAutoCommit(true);
            DbUtils.close(resultSet);
            DbUtils.close(statement);
            DbUtils.close(connection);
        }
        return key;
    }


    @Override
    public User getById(int id) {
        LOGGER.info("Started the method read with User id= " + id);
        User user = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(Constants.GET_USER_BY_ID)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                user = extractUser(rs);
            }
            LOGGER.info("User successfully received from database. User: " + user);
        } catch (SQLException | NullPointerException e) {
            LOGGER.error("Cannot read user from database, user_id = " + id, e);
            throw new DAOException("Cannot read user from database, user_id = " + id, e);
        }
        return user;
    }

    @Override
    public boolean update(User user) throws SQLException {
        LOGGER.info("Started the method update. User: " + user);
        boolean updateUser;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);
            LOGGER.info("Transaction started");
            statement = connection.prepareStatement(Constants.UPDATE_USER);
            setStatement(user, statement);
            statement.setInt(10, user.getUserId());
            updateUser = statement.executeUpdate() > 0;
            connection.commit();
            LOGGER.info("Transaction done");
        } catch (SQLException e) {
            assert connection != null;
            connection.rollback();
            LOGGER.error(e.getClass() + " in method update: " + e);
            throw new DAOException("Cannot update user, user = " + user, e);
        } finally {
            assert connection != null;
            connection.setAutoCommit(true);
            DbUtils.close(statement);
            DbUtils.close(connection);
        }
        return updateUser;
    }

    @Override
    public void delete(int id) {
        LOGGER.info("Started the method delete with User id= " + id);
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(Constants.DELETE_USER)) {
            statement.setInt(1, id);
            statement.executeUpdate();
            LOGGER.info("Delete method done");
        } catch (SQLException e) {
            LOGGER.error("Cannot delete user with id = " + id, e);
            throw new DAOException("Cannot delete user with id = " + id, e);
        }

    }

    @Override
    public User getUserByEmail(String email) throws SQLException {
        LOGGER.info("Started the method getUserByEmail with email= " + email);
        User user = null;
        ResultSet resultSet = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(Constants.GET_USER_BY_EMAIL)) {
            statement.setString(1, email);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = extractUser(resultSet);
            }
            LOGGER.info("User successfully received from database. User: " + user);
        } catch (SQLException | NullPointerException e) {
            LOGGER.error(e.getClass() + " in method getUserByEmail: " + e);
            throw new DAOException("Cannot extract user with email = " + email, e);
        } finally {
            DbUtils.close(resultSet);
        }
        return user;
    }


    @Override
    public boolean checkUserByEmail(String email) {
        LOGGER.info("Started the method checkUserByEmail with email= " + email);
        boolean userExist;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(Constants.GET_USER_BY_EMAIL)) {
            statement.setString(1, email);
            userExist = statement.executeQuery().next();
        } catch (SQLException | NullPointerException e) {
            LOGGER.error(e.getClass() + " in method checkUserByEmail: " + e);
            throw new DAOException("User is not valid with email = " + email, e);
        }
        LOGGER.info("checkUserByEmail method done");
        return userExist;
    }

    @Override
    public List<User> getUsersByRole(String role, int offset, int limit, Map<String, String> search) throws SQLException {
        LOGGER.info("Started the method getUsersByRole with role: " + role + " offset: " + offset + " limit: " + limit
                + "\nsearch: " + search.toString());
        String searchQuery = search.isEmpty() ? "" : buildSearchQuery(search);
        ResultSet resultSet = null;
        List<User> users = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(String.format(Constants.GET_USERS_BY_ROLE, searchQuery))) {
            statement.setString(1, role.toLowerCase());
            statement.setInt(2, offset);
            statement.setInt(3, limit);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                users.add(extractUser(resultSet));
            }
        } catch (SQLException | NullPointerException e) {
            LOGGER.error(e.getClass() + " in method getUsersByRole: " + e);
            throw new DAOException("Cannot extract list of users by role = " + role, e);
        } finally {
            DbUtils.close(resultSet);
        }
        LOGGER.info("Method getUsersByRole done with users: " + users);
        return users;
    }

    private String buildSearchQuery(Map<String, String> search) {
        StringBuilder stringBuilder = new StringBuilder("AND ( ");
        final int[] count = {0};
        search.forEach((key, value) -> {
            if (count[0] < 1) {
                stringBuilder.append(key).append(" REGEXP ").append("'").append(value).append("'");
                count[0]++;
            } else {
                stringBuilder.append(" AND ").append(key).append(" REGEXP ").append("'").append(value).append("'");
            }
        });
        return stringBuilder.append(" )").toString();
    }

    @Override
    public void updateBlocked(int id, boolean status) {
        LOGGER.info("Started the method updateBlocked with id: " + id + " and status: " + status);
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(Constants.UPDATE_BLOCKED)) {
            statement.setBoolean(1, status);
            statement.setInt(2, id);
            statement.executeUpdate();
            LOGGER.info("The method updateBlocked done");
        } catch (SQLException | NullPointerException e) {
            LOGGER.error(e.getClass() + " in method updateBlocked: " + e);
            throw new DAOException("Cannot change status = " + status + " with id = " + id, e);
        }
    }

    @Override
    public void updateRememberUserToken(int id, String token) {
        LOGGER.info("Started the method updateRememberUserToken with id: " + id + " and token: " + token);
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(Constants.UPDATE_USER_LOG_IN_TOKEN_BY_ID)) {
            preparedStatement.setString(1, token);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
            LOGGER.info("The method updateRememberUserToken done");
        } catch (SQLException e) {
            LOGGER.error(String.format("%s in updateRememberUserToken User id: %d, token %s update error", e.getClass(), id, token), e);
            throw new DAOException("Service common Error", e);
        }
    }

    @Override
    public User findUserByIdAndToken(int userId, String token) {
        LOGGER.info("Started the method findUserByIdAndToken with id: " + userId + " and token: " + token);
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(Constants.FIND_USER_BY_ID_AND_TOKEN)) {
            System.out.println(userId + ":" + token);
            preparedStatement.setInt(1, userId);
            preparedStatement.setString(2, token);
            ResultSet resultSet = preparedStatement.executeQuery();
            User user = null;
            if (resultSet.next()) {
                user = extractUser(resultSet);
            }
            LOGGER.info("The method findUserByIdAndToken done. User: " + user);
            return user;
        } catch (SQLException | DAOException e) {
            LOGGER.error(String.format("%s in findUserByIdAndToken. User id: %d, token %s finding error", e.getClass(), userId, token), e);
            throw new DAOException("Service common Error", e);
        }
    }

    @Override
    public void deleteRememberUserToken(int userId) {
        LOGGER.info("Started the method deleteRememberUserToken with id: " + userId);
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(Constants.UPDATE_USER_LOG_IN_TOKEN_TO_NULL)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.executeUpdate();
            LOGGER.info("The method deleteRememberUserToken done");
        } catch (SQLException e) {
            LOGGER.warn(String.format("%s in deleteRememberUserToken. User id: %d, token delete error", e.getClass(), userId), e);
            throw new DAOException("Service common Error", e);
        }
    }

    @Override
    public int getUserCount(Map<String, String> search) throws SQLException {
        LOGGER.info("Started the method getUserCount");
        String searchQuery = search.isEmpty() ? "" : buildSearchQuery(search);
        LOGGER.info("searchQuery: " + searchQuery);
        int userCount = 0;
        ResultSet resultSet = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(String.format(Constants.GET_COUNT_OF_USERS, searchQuery))) {
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                userCount = resultSet.getInt(AppContextConstant.COUNT);
            }
            LOGGER.info("The method getUserCount done. Count of users: " + userCount);
        } catch (SQLException e) {
            LOGGER.info(e.getClass() + " in method getUserCount: " + e.getMessage());
            throw new DAOException("Cannot extract number of users", e);
        } finally {
            DbUtils.close(resultSet);
        }
        return userCount;
    }

    private User extractUser(ResultSet rs) throws SQLException {
        return new UserBuilder().setUserId(rs.getInt(AppContextConstant.ID))
                .setEmail(rs.getString(AppContextConstant.EMAIL))
                .setPassword(rs.getString(AppContextConstant.PASSWORD))
                .setFirstName(rs.getString(AppContextConstant.FIRST_NAME))
                .setLastName(rs.getString(AppContextConstant.LAST_NAME))
                .setPhone(rs.getString(AppContextConstant.PHONE_NUMBER))
                .setBirthDate(rs.getObject(AppContextConstant.BIRTH_DATE, LocalDate.class))
                .setRole(Role.valueOf(rs.getString(AppContextConstant.ROLE).toUpperCase()))
                .setBlocked(rs.getBoolean(AppContextConstant.BLOCKED))
                .setLogInToken(rs.getString(AppContextConstant.LOGIN_TOKEN)).build();
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
}