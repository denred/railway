package com.epam.redkin.railway.model.service;


import com.epam.redkin.railway.model.entity.User;

import java.util.List;
import java.util.Map;

public interface UserService {

    User isValidUser(String login, String password);

    int registerUser(User user, String url);

    void updateBlocked(int idUser, boolean blockStatus);

    User read(int userId);

    List<User> getUserList(int offset, int limit, Map<String, String> search);

    void sendLogInTokenIfForgetPassword(String email, String pageRootUrl);
    String getUpdatedRememberUserToken(int id);

    User logInByToken(String token);

    void deleteRememberUserToken(int userId);

    void postRegistrationApprovalByToken(String token);

    int getUserCount(Map<String, String> search);

    boolean updateUser(User newUser);
}
