package com.epam.redkin.model.repository.impl;

import com.epam.redkin.model.entity.*;
import com.epam.redkin.model.repository.UserRepo;

import java.time.LocalDate;

public class TestDBAddNewUser {
    public static void main(String[] args) {
       User admin = new User("admin@mail.com", "admin", "Nicolas", "Semerenko", "+30667771122", LocalDate.now(), Role.ADMIN, false);
       UserRepo up = new UserRepoImpl();

       up.create(admin);
    }
}
