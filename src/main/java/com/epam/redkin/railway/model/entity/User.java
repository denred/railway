package com.epam.redkin.railway.model.entity;

import com.epam.redkin.railway.model.builder.UserBuilder;
import org.mindrot.jbcrypt.BCrypt;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * User entity class
 *
 * @author Denis Redkin
 */
public class User implements Serializable, Comparable<User> {
    private static final long serialVersionUID = 1L;
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
    private String token;

    public User(UserBuilder builder) {
        this.userId = builder.getUserId();
        this.email = builder.getEmail();
        this.password = builder.getPassword();
        this.firstName = builder.getFirstName();
        this.lastName = builder.getLastName();
        this.phone = builder.getPhone();
        this.birthDate = builder.getBirthDate();
        this.role = builder.getRole();
        this.roleId = builder.getRoleId();
        this.blocked = builder.isBlocked();
        this.token = builder.getLogInToken();
    }

    public User() {
    }

    public User(String email, String password, String firstName, String lastName, String phone, LocalDate birthDate, Role role, boolean blocked) {
        this.email = email;
        this.password = BCrypt.hashpw(password, BCrypt.gensalt());
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.birthDate = birthDate;
        this.role = role;
        this.blocked = blocked;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phone='" + phone + '\'' +
                ", birthDate=" + birthDate +
                ", role=" + role +
                ", blocked=" + blocked +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return getRoleId() == user.getRoleId() && isBlocked() == user.isBlocked() && Objects.equals(getEmail(), user.getEmail()) && Objects.equals(getPassword(), user.getPassword()) && Objects.equals(getFirstName(), user.getFirstName()) && Objects.equals(getLastName(), user.getLastName()) && Objects.equals(getPhone(), user.getPhone()) && Objects.equals(getBirthDate(), user.getBirthDate()) && getRole() == user.getRole();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEmail(), getPassword(), getFirstName(), getLastName(), getPhone(), getBirthDate(), getRole(), getRoleId(), isBlocked());
    }

    @Override
    public int compareTo(User o) {
        return email.compareTo(o.getEmail());
    }
}
