package com.epam.redkin.railway.web.controller.command.user;

import com.epam.redkin.railway.model.service.UserService;
import com.epam.redkin.railway.model.validator.LoginValidator;
import com.epam.redkin.railway.web.controller.command.Command;
import com.epam.redkin.railway.web.controller.command.Router;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.epam.redkin.railway.util.constants.AppContextConstant.EMAIL;
import static com.epam.redkin.railway.web.controller.Path.COMMAND_GET_LOGIN_PAGE;

public class ForgetPasswordEmailSendingCommand implements Command {
    private final static Logger LOGGER = LoggerFactory.getLogger(ForgetPasswordEmailSendingCommand.class);
    private final UserService userService;

    public ForgetPasswordEmailSendingCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("started");

        LoginValidator loginValidator = new LoginValidator();

        String email = request.getParameter(EMAIL);

        if (StringUtils.isNoneBlank(email) && loginValidator.isValidEmail(email)) {
            userService.sendLogInTokenIfForgetPassword(email, request.getRequestURL().toString());
        }
        LOGGER.info("done");
        return Router.redirect(COMMAND_GET_LOGIN_PAGE);
    }
}
