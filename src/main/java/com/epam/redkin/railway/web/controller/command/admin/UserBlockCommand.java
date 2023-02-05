package com.epam.redkin.railway.web.controller.command.admin;

import com.epam.redkin.railway.model.service.UserService;
import com.epam.redkin.railway.web.controller.Path;
import com.epam.redkin.railway.web.controller.command.Command;
import com.epam.redkin.railway.appcontext.AppContext;
import com.epam.redkin.railway.web.controller.command.Router;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.epam.redkin.railway.util.constants.AppContextConstant.BLOCK_STATUS;
import static com.epam.redkin.railway.util.constants.AppContextConstant.USER_ID;

public class UserBlockCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserBlockCommand.class);

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("started");
        Router router = new Router();
        router.setRouteType(Router.RouteType.REDIRECT);
        router.setPagePath(Path.COMMAND_INFO_USERS);
        UserService userService = AppContext.getInstance().getUserService();
        String userId = request.getParameter(USER_ID);
        String userStatus = request.getParameter(BLOCK_STATUS);
        boolean blockStatus = Boolean.parseBoolean(userStatus);
        userService.updateBlocked(Integer.parseInt(userId), blockStatus);
        LOGGER.info("done");
        return router;
    }
}
