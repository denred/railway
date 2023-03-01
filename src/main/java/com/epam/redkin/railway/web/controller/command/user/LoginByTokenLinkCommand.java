package com.epam.redkin.railway.web.controller.command.user;

import com.epam.redkin.railway.model.entity.User;
import com.epam.redkin.railway.model.service.UserService;
import com.epam.redkin.railway.web.controller.command.Command;
import com.epam.redkin.railway.web.controller.command.Router;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;

import static com.epam.redkin.railway.util.constants.AppContextConstant.*;
import static com.epam.redkin.railway.web.controller.Path.COMMAND_HOME;

public class LoginByTokenLinkCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginByTokenLinkCommand.class);
    private final UserService userService;

    public LoginByTokenLinkCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("Starting execution of LoginByTokenLinkCommand");

        HttpSession session = request.getSession();
        String token = (String) session.getAttribute(COOKIE_REMEMBER_USER_TOKEN);
        LOGGER.info("User token from the request: {}", token);

        if (StringUtils.isBlank(token)) {
            LOGGER.info("Token is not present in the request, redirecting to homepage");
            return Router.redirect(COMMAND_HOME);
        }

        User user = userService.logInByToken(token);
        session.setAttribute(SESSION_USER, user);
        String locale = (String) session.getAttribute(LOCALE);
        if (StringUtils.isBlank(locale)) {
            ServletContext context = request.getServletContext();
            locale = (String) context.getAttribute(LOCALE);
        }
        session.setAttribute(LOCALE, locale);
        session.setAttribute(CURRENT_DATE, LocalDate.now());
        userService.deleteRememberUserToken(user.getUserId());

        LOGGER.info("User with id {} has been successfully logged in by token", user.getUserId());
        return Router.redirect(COMMAND_HOME);
    }
}
