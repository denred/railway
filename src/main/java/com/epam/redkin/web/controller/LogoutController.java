package com.epam.redkin.web.controller;


import com.epam.redkin.service.LogoutService;
import com.epam.redkin.util.constants.AppContextConstant;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/logout")
public class LogoutController extends HttpServlet {
  //  private static final Logger LOGGER = Logger.getLogger(LogoutController.class);

    private LogoutService logoutService;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logoutService.logout(request);
        response.sendRedirect("login.jsp");
    }

    @Override
    public void init(ServletConfig config) {
        logoutService = (LogoutService) config.getServletContext().getAttribute(AppContextConstant.LOGOUT_SERVICE);
        //LOGGER.trace("logout Servlet init");

    }
}