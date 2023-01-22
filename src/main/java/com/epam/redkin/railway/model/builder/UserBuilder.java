package com.epam.redkin.railway.model.builder;

import com.epam.redkin.railway.model.entity.Role;
import com.epam.redkin.railway.model.entity.User;
import com.epam.redkin.railway.model.exception.IncorrectDataException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Date;
import java.time.LocalDate;

public class UserBuilder {
    private final static Logger LOGGER = LoggerFactory.getLogger(UserBuilder.class);
    private int userId;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String phone;
    private LocalDate birthDate;
    private Role role;
    private int roleId;
    private boolean blocked;
    private String logInToken;


    public int getUserId() {
        return userId;
    }

    public UserBuilder setUserId(int userId) {
        this.userId = userId;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserBuilder setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserBuilder setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public UserBuilder setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserBuilder setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public UserBuilder setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public UserBuilder setBirthDate(LocalDate birthDate) {
        try {
            this.birthDate = birthDate;
        } catch (IllegalArgumentException e) {
            LOGGER.error("Incorrect data entered. Please enter correct birth date.");
            throw new IncorrectDataException("Incorrect data entered, birth date", e);
        }
        return this;
    }

    public Role getRole() {
        return role;
    }

    public UserBuilder setRole(Role role) {
        this.role = role;
        return this;
    }

    public int getRoleId() {
        return roleId;
    }

    public UserBuilder setRoleId(int roleId) {
        this.roleId = roleId;
        return this;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public UserBuilder setBlocked(boolean blocked) {
        this.blocked = blocked;
        return this;
    }

    public String getLogInToken() {
        return logInToken;
    }

    public UserBuilder setLogInToken(String logInToken) {
        this.logInToken = logInToken;
        return this;
    }

    public User build() {
        return new User(this);
    }
}
