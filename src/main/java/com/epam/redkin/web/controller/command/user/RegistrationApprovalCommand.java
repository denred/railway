package com.epam.redkin.web.controller.command.user;

import com.epam.redkin.model.service.UserService;
import com.epam.redkin.web.controller.command.Command;
import com.epam.redkin.web.listener.AppContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.epam.redkin.web.controller.Path.COMMAND_LOGIN_BY_TOKEN_LINK;
import static com.epam.redkin.web.controller.Path.COOKIE_REMEMBER_USER_TOKEN;

public class RegistrationApprovalCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationApprovalCommand.class);
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("started");
        UserService userService = AppContext.getInstance().getUserService();
        String token = request.getParameter(COOKIE_REMEMBER_USER_TOKEN);
        userService.postRegistrationApprovalByToken(token);
        LOGGER.info("done");
        return COMMAND_LOGIN_BY_TOKEN_LINK;
    }
}
