package com.epam.redkin.railway.web.controller.command.common;

import com.epam.redkin.railway.model.entity.Role;
import com.epam.redkin.railway.model.entity.User;
import com.epam.redkin.railway.model.service.UserService;
import com.epam.redkin.railway.model.validator.LoginValidator;
import com.epam.redkin.railway.web.controller.Path;
import com.epam.redkin.railway.web.controller.command.Command;
import com.epam.redkin.railway.appcontext.AppContext;
import com.epam.redkin.railway.web.controller.command.Router;
import com.epam.redkin.railway.web.controller.command.SupportedLocaleStorage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.time.LocalDate;
import java.util.Locale;

import static com.epam.redkin.railway.util.constants.AppContextConstant.*;
import static com.epam.redkin.railway.web.controller.Path.COMMAND_GET_LOGIN_PAGE;

public class LoginCommand implements Command {
    private final AppContext appContext;
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginCommand.class);

    public LoginCommand(AppContext appContext) {
        this.appContext = appContext;
    }

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("Started LoginCommand execution");
        UserService userService = appContext.getUserService();
        LoginValidator loginValidator = new LoginValidator();

        String locale = SupportedLocaleStorage.getLocaleFromLanguage(Locale.getDefault().getLanguage()).getLanguage();

        HttpSession session = request.getSession();
        String login = request.getParameter(USER_LOGIN);
        String password = request.getParameter(PASSWORD);

        if (login == null || password == null || login.isEmpty() || password.isEmpty()) {
            session.setAttribute(ERROR_MESSAGE, ERROR_MESSAGE_LOGIN);
            LOGGER.error("Login or password is empty");
            return Router.redirect(COMMAND_GET_LOGIN_PAGE);
        }

        if (!loginValidator.isValid(login, password)) {
            session.setAttribute(ERROR_MESSAGE, "Login or password is invalid");
            LOGGER.error("Login - {} or password - {} is invalid", login, password);
            return Router.redirect(COMMAND_GET_LOGIN_PAGE);
        }

        User user = userService.isValidUser(login, password);
        LOGGER.info("User successfully authenticated: {}", user);

        session.removeAttribute(ERROR_MESSAGE);
        session.setAttribute(CURRENT_DATE, LocalDate.now());
        session.setAttribute(SESSION_USER, user);
        session.setAttribute(LOCALE, locale);

        Router router;
        if (user.getRole() == Role.USER) {
            router = Router.redirect(Path.COMMAND_PROFILE);
        } else {
            router = Router.redirect(Path.COMMAND_HOME);
        }
        LOGGER.info("Finished LoginCommand execution");
        return router;
    }
}