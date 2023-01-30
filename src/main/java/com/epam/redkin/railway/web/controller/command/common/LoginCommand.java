package com.epam.redkin.railway.web.controller.command.common;

import com.epam.redkin.railway.model.entity.User;
import com.epam.redkin.railway.model.service.UserService;
import com.epam.redkin.railway.web.controller.Path;
import com.epam.redkin.railway.web.controller.command.Command;
import com.epam.redkin.railway.appcontext.AppContext;
import com.epam.redkin.railway.web.controller.command.SupportedLocaleStorage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

import static com.epam.redkin.railway.util.constants.AppContextConstant.*;

public class LoginCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("started");
        UserService userService = AppContext.getInstance().getUserService();
        HttpSession session = request.getSession();
        String locale = SupportedLocaleStorage.getLocaleFromLanguage(Locale.getDefault().getLanguage()).getLanguage();
        LOGGER.info("Current locale: " + locale);

        String login = request.getParameter(USER_LOGIN);
        String password = request.getParameter(PASSWORD);
        String forward = Path.PAGE_LOGIN;

        if (login == null || password == null || login.isEmpty() || password.isEmpty()) {
            request.setAttribute(ERROR_MESSAGE, "Login or password can't be empty");
            LOGGER.info("Login or password is empty");
            return forward;
        }
        User user = userService.isValidUser(login, password);
        LOGGER.info("User successfully extracted");
        forward = Path.COMMAND_HOME;
        session.setAttribute(SESSION_USER, user);
        session.setAttribute(CURRENT_DATE_TIME, LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        session.setAttribute(LOCALE, locale);
        LOGGER.info("done");
        return forward;
    }
}