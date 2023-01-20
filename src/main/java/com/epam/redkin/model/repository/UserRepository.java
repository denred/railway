package com.epam.redkin.model.repository;

import com.epam.redkin.model.entity.User;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * DAO for the {@link User} objects.
 * Interface provides methods receive the user by his e-mail, check if the registered user is
 * valid by the given e-mail, receives full information about the user, updates the status of the user’s blocking
 *
 * @author Denys Redkin
 */
public interface UserRepository extends EntityDAO<User> {
    User getUserByEmail(String email) throws SQLException;

    boolean checkUserByEmail(String email);

    List<User> getUsersByRole(String role, int offset, int limit, Map<String, String> search) throws SQLException;

    void updateBlocked(int id, boolean status);

    void updateRememberUserToken(int id, String token);

    User findUserByIdAndToken(int userId, String tokenValue);

    void deleteRememberUserToken(int userId);

    int getUserCount(Map<String, String> search) throws SQLException;
}
