package com.epam.redkin.railway.model.entity;

/**
 * User entity class
 *
 * @author Denis Redkin
 */
public enum Role {
    ADMIN, USER;

    public static Role getRole(User user) {
        int roleId = user.getRoleId();
        return Role.values()[--roleId];
    }

    public String getName() {
        return name().toLowerCase();
    }
}
