package com.epam.redkin.web.controller;

import com.epam.redkin.model.entity.User;
import com.epam.redkin.model.exception.IncorrectDataException;
import com.epam.redkin.service.UserService;
import com.epam.redkin.util.constants.AppContextConstant;
import com.epam.redkin.validator.LoginValidator;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@WebServlet("/login")
public class LoginController extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);
    private UserService userService;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        LoginValidator loginValidator = new LoginValidator();
        try {
            String login = request.getParameter("login");
            String password = request.getParameter("password");
            loginValidator.isValid(login, password);
            User user = userService.isValidUser(login, password);
            HttpSession session = request.getSession();
            if (user != null) {
                session.setAttribute(AppContextConstant.SESSION_USER, user);
                response.sendRedirect("home");
            }
        } catch (NumberFormatException e) {
            LOGGER.error("Incorrect data entered");
            throw new IncorrectDataException("Incorrect data entered");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    public void init(ServletConfig config) {
        userService = (UserService) config.getServletContext().getAttribute(AppContextConstant.USER_SERVICE);
        LOGGER.debug("login Servlet init");
    }
}