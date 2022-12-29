package com.epam.redkin.service;


import com.epam.redkin.model.entity.User;

import java.util.List;

public interface UserService {

    User isValidUser(String login, String password);

    int registr(User user);

    List<User> getUserInfo(String userRole);

    void updateBlocked(int idUser, boolean blockStatus);

    User read(int userId);
}
