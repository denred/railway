package com.epam.redkin.railway.model.service;


import jakarta.servlet.http.HttpServletRequest;

public interface LogoutService {

    void logout(HttpServletRequest request);
}
