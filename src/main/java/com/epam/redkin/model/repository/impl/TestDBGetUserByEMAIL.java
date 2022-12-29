package com.epam.redkin.model.repository.impl;

import com.epam.redkin.model.entity.User;
import com.epam.redkin.model.repository.UserRepo;

public class TestDBGetUserByEMAIL {
    public static void main(String[] args) {
        UserRepo ur = new UserRepoImpl();
        User newUser = ur.getByEmail("admin@mail.com");
        System.out.println(newUser.toString());
        System.out.println(ur.read(1).toString());
    }
}
