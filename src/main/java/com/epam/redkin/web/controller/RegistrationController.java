package com.epam.redkin.web.controller;

import com.epam.redkin.model.entity.Role;
import com.epam.redkin.model.entity.User;
import com.epam.redkin.model.exception.IncorrectDataException;
import com.epam.redkin.model.service.UserService;
import com.epam.redkin.util.constants.AppContextConstant;
import com.epam.redkin.model.validator.RegistrationValidator;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.Date;

@WebServlet("/registration")
public class RegistrationController extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationController.class);

    private UserService userService;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = new User();
        RegistrationValidator registrationValidator = new RegistrationValidator();
        HttpSession session = request.getSession();
        user.setEmail(request.getParameter("email"));
        user.setPassword(request.getParameter("password"));
        user.setFirstName(request.getParameter("first_name"));
        user.setLastName(request.getParameter("last_name"));
        user.setPhone(request.getParameter("phone"));
        user.setRole(Role.USER);
        user.setBlocked(false);
        LOGGER.debug(user.toString());
        session.setAttribute(AppContextConstant.SESSION_USER, user);
        try {
            Date birthDate = Date.valueOf(request.getParameter("birth_date"));
            user.setBirthDate(birthDate.toLocalDate());

        } catch (IllegalArgumentException e) {
            LOGGER.error("Incorrect data entered");
            throw new IncorrectDataException("Incorrect data entered", e);
        }
        registrationValidator.isValidClientRegister(user);
        int id = userService.registerUser(user, request.getRequestURL().toString());
        user.setUserId(id);
        response.sendRedirect("home");
    }

    @Override
    public void init(ServletConfig config) {
        userService = (UserService) config.getServletContext().getAttribute(AppContextConstant.USER_SERVICE);
        LOGGER.trace("registration Servlet init");
    }
}