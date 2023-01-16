package com.epam.redkin.web.controller.command.user;

import com.epam.redkin.model.exception.ServiceException;
import com.epam.redkin.model.service.UserService;
import com.epam.redkin.web.controller.command.Command;
import com.epam.redkin.web.listener.AppContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.epam.redkin.web.controller.Path.EMAIL;
import static com.epam.redkin.web.controller.Path.PAGE_FORGET_PASSWORD;

public class ForgetPasswordEmailSendingCommand implements Command {
    private final static Logger LOGGER = LoggerFactory.getLogger(ForgetPasswordEmailSendingCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        UserService userService = AppContext.getInstance().getUserService();
        String redirectURL = PAGE_FORGET_PASSWORD;
        String email = request.getParameter(EMAIL);
        if(StringUtils.isNoneBlank(email)) {
            try {
                userService.sendLogInTokenIfForgetPassword(email, request.getRequestURL().toString());
            } catch (ServiceException e) {
                LOGGER.error("Incorrect data entered", e);
                throw new ServiceException(e.getMessage());
            }
        }
        return redirectURL;
    }
}
