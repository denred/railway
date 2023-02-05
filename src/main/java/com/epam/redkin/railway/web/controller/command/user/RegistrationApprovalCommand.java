package com.epam.redkin.railway.web.controller.command.user;

import com.epam.redkin.railway.model.service.UserService;
import com.epam.redkin.railway.util.constants.AppContextConstant;
import com.epam.redkin.railway.web.controller.Path;
import com.epam.redkin.railway.web.controller.command.Command;
import com.epam.redkin.railway.appcontext.AppContext;
import com.epam.redkin.railway.web.controller.command.Router;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class RegistrationApprovalCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationApprovalCommand.class);
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("started");
        Router router = new Router();
        router.setRouteType(Router.RouteType.REDIRECT);
        router.setPagePath(Path.COMMAND_LOGIN_BY_TOKEN_LINK);
        UserService userService = AppContext.getInstance().getUserService();
        String token = request.getParameter(AppContextConstant.COOKIE_REMEMBER_USER_TOKEN);
        userService.postRegistrationApprovalByToken(token);
        LOGGER.info("done");
        return router;
    }
}
