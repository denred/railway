package com.epam.redkin.railway.web.controller.command.user;

import com.epam.redkin.railway.model.service.UserService;
import com.epam.redkin.railway.web.controller.command.Command;
import com.epam.redkin.railway.appcontext.AppContext;
import com.epam.redkin.railway.web.controller.command.Router;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.epam.redkin.railway.util.constants.AppContextConstant.COOKIE_REMEMBER_USER_TOKEN;
import static com.epam.redkin.railway.web.controller.Path.COMMAND_LOGIN_BY_TOKEN_LINK;


public class RegistrationApprovalCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationApprovalCommand.class);

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("Started registration approval command");
        UserService userService = AppContext.getInstance().getUserService();
        HttpSession session = request.getSession();
        String token = request.getParameter(COOKIE_REMEMBER_USER_TOKEN);
        userService.postRegistrationApprovalByToken(token);
        session.setAttribute(COOKIE_REMEMBER_USER_TOKEN, token);

        LOGGER.info("Registration approval command done");
        return Router.redirect(COMMAND_LOGIN_BY_TOKEN_LINK);
    }
}
