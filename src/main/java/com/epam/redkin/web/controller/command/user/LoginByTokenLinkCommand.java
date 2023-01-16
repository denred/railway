package com.epam.redkin.web.controller.command.user;

import com.epam.redkin.model.entity.User;
import com.epam.redkin.model.exception.ServiceException;
import com.epam.redkin.model.service.UserService;
import com.epam.redkin.web.controller.command.Command;
import com.epam.redkin.web.listener.AppContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.epam.redkin.web.controller.Path.*;

public class LoginByTokenLinkCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginByTokenLinkCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("started");
        UserService userService = AppContext.getInstance().getUserService();
        String redirectURL = PAGE_LOGIN;
        String token = request.getParameter(COOKIE_REMEMBER_USER_TOKEN);
        try {
            User user = userService.logInByToken(token);
            request.getSession().setAttribute(USER_LOGIN, user.getEmail());
            request.getSession().setAttribute(USER_ROLE, user.getRole().name());
            request.getSession().setAttribute(USER_ID, user.getUserId());
            userService.deleteRememberUserToken(user.getUserId());
            redirectURL = PAGE_HOME;
        } catch (ServiceException e) {
            LOGGER.error("Incorrect data entered", e);
            throw new ServiceException("Incorrect data entered");
        }
        LOGGER.info("done");
        return redirectURL;
    }
}
