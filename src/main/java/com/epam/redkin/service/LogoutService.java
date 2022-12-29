package com.epam.redkin.service;


import jakarta.servlet.http.HttpServletRequest;

public interface LogoutService {

    void logout(HttpServletRequest request);
}
