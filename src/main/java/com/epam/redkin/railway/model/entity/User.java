package com.epam.redkin.railway.model.entity;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
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
    private LocalDate registrationDate;
    private Role role;
    private int roleId;
    private boolean blocked;
    private String token;
    private Double balance;
}
