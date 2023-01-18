package com.epam.redkin.web.controller.command.common;

import com.epam.redkin.model.entity.User;
import com.epam.redkin.model.service.UserService;
import com.epam.redkin.web.controller.command.Command;
import com.epam.redkin.web.listener.AppContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static com.epam.redkin.util.constants.AppContextConstant.*;
import static com.epam.redkin.web.controller.Path.*;

public class LoginCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("started");
        UserService userService = AppContext.getInstance().getUserService();
        HttpSession session = request.getSession();

        String login = request.getParameter(USER_LOGIN);
        String password = request.getParameter(PASSWORD);
        String forward = PAGE_LOGIN;

        if (login == null || password == null || login.isEmpty() || password.isEmpty()) {
            request.setAttribute(ERROR_MESSAGE, "Login or password can't be empty");
            LOGGER.info("Login or password is empty");
            return forward;
        }
        User user = userService.isValidUser(login, password);
        LOGGER.info("User successfully extracted");
        forward = COMMAND_HOME;
        session.setAttribute(SESSION_USER, user);
        session.setAttribute(CURRENT_DATE_TIME, LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        session.setAttribute(LOCALE, LOCALE_UA);
        LOGGER.info("done");
        return forward;
    }
}