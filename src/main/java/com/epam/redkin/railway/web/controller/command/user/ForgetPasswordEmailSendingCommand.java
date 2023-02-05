package com.epam.redkin.railway.web.controller.command.user;

import com.epam.redkin.railway.model.exception.ServiceException;
import com.epam.redkin.railway.model.service.UserService;
import com.epam.redkin.railway.util.constants.AppContextConstant;
import com.epam.redkin.railway.web.controller.Path;
import com.epam.redkin.railway.web.controller.command.Command;
import com.epam.redkin.railway.appcontext.AppContext;
import com.epam.redkin.railway.web.controller.command.Router;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ForgetPasswordEmailSendingCommand implements Command {
    private final static Logger LOGGER = LoggerFactory.getLogger(ForgetPasswordEmailSendingCommand.class);

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("started");
        Router router = new Router();
        router.setRouteType(Router.RouteType.FORWARD);
        router.setPagePath(Path.PAGE_FORGET_PASSWORD);
        UserService userService = AppContext.getInstance().getUserService();
        String email = request.getParameter(AppContextConstant.EMAIL);
        if(StringUtils.isNoneBlank(email)) {
            try {
                userService.sendLogInTokenIfForgetPassword(email, request.getRequestURL().toString());
                router.setPagePath(Path.PAGE_LOGIN);
            } catch (ServiceException e) {
                LOGGER.error("Incorrect data entered", e);
                throw new ServiceException(e.getMessage());
            }
        }
        LOGGER.info("done");
        return router;
    }
}
