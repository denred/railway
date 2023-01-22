package com.epam.redkin.railway.web.controller.command.user;

import com.epam.redkin.railway.model.exception.ServiceException;
import com.epam.redkin.railway.model.service.UserService;
import com.epam.redkin.railway.util.constants.AppContextConstant;
import com.epam.redkin.railway.web.controller.Path;
import com.epam.redkin.railway.web.controller.command.Command;
import com.epam.redkin.railway.appcontext.AppContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ForgetPasswordEmailSendingCommand implements Command {
    private final static Logger LOGGER = LoggerFactory.getLogger(ForgetPasswordEmailSendingCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("started");
        UserService userService = AppContext.getInstance().getUserService();
        String redirectURL = Path.PAGE_FORGET_PASSWORD;
        String email = request.getParameter(AppContextConstant.EMAIL);
        if(StringUtils.isNoneBlank(email)) {
            try {
                userService.sendLogInTokenIfForgetPassword(email, request.getRequestURL().toString());
                redirectURL = Path.PAGE_LOGIN;
            } catch (ServiceException e) {
                LOGGER.error("Incorrect data entered", e);
                throw new ServiceException(e.getMessage());
            }
        }
        LOGGER.info("done");
        return redirectURL;
    }
}
