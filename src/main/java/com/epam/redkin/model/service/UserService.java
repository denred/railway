package com.epam.redkin.model.service;


import com.epam.redkin.model.entity.User;

import java.util.List;

public interface UserService {

    User isValidUser(String login, String password);

    int registerUser(User user, String s);

    List<User> getUserInfo(String userRole);

    void updateBlocked(int idUser, boolean blockStatus);

    User read(int userId);

    List<User> getUserListByCurrentRecordAndRecordsPerPage(int currentPage, int recordsPerPage);

    int getUserListSize();

    void sendLogInTokenIfForgetPassword(String email, String pageRootUrl);
    String getUpdatedRememberUserToken(int id);

    User logInByToken(String token);

    void deleteRememberUserToken(int userId);
}
