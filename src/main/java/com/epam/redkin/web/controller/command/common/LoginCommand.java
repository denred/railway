package com.epam.redkin.web.controller.command.common;

import com.epam.redkin.model.entity.User;
import com.epam.redkin.model.exception.IncorrectDataException;
import com.epam.redkin.service.UserService;
import com.epam.redkin.util.constants.AppContextConstant;
import com.epam.redkin.validator.LoginValidator;
import com.epam.redkin.web.controller.command.Command;
import com.epam.redkin.web.listener.AppContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        UserService userService = AppContext.getInstance().getUserService();
        LoginValidator loginValidator = new LoginValidator();
        HttpSession session = request.getSession();
        try {
            String login = request.getParameter("login");
            String password = request.getParameter("password");
            loginValidator.isValid(login, password);

            User user = userService.isValidUser(login, password);

            if (user != null) {
                session.setAttribute(AppContextConstant.LOCALE, AppContextConstant.LOCALE_UA);
                session.setAttribute(AppContextConstant.SESSION_USER, user);
                response.sendRedirect("home");
            }
        } catch (NumberFormatException e) {
            LOGGER.error("Incorrect data entered");
            throw new IncorrectDataException("Incorrect data entered");
        }
        return null;
    }


}
