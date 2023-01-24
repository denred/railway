package com.epam.redkin.railway.model.entity;

import lombok.Builder;
import lombok.Data;
import org.mindrot.jbcrypt.BCrypt;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
@Data
@Builder
public class User {
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
}
