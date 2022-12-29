package com.epam.redkin.model.repository;

import com.epam.redkin.model.entity.User;

import java.util.List;
/**
 * DAO for the {@link User} objects.
 * Interface provides methods receive the user by his e-mail, check if the registered user is
 * valid by the given e-mail, receives full information about the user, updates the status of the user’s blocking
 *
 * @author Denys Redkin
 */
public interface UserRepo extends EntityDAO<User> {
    User getByEmail(String email);

    boolean checkUser(String email);

    List<User> getUsersByRole(String role);

    void updateBlocked(int id, boolean status);
}
