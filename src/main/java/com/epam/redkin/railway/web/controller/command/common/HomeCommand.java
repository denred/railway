package com.epam.redkin.railway.web.controller.command.common;

import com.epam.redkin.railway.model.entity.User;
import com.epam.redkin.railway.util.constants.AppContextConstant;
import com.epam.redkin.railway.web.controller.Path;
import com.epam.redkin.railway.web.controller.command.Command;
import com.epam.redkin.railway.web.controller.command.Router;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.epam.redkin.railway.util.constants.AppContextConstant.*;

public class HomeCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(HomeCommand.class);
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("started");
        HttpSession session = request.getSession();
        Router router = new Router();
        router.setRouteType(Router.RouteType.FORWARD);
        router.setPagePath(Path.PAGE_HOME);
        User user = (User) session.getAttribute(SESSION_USER);
        request.setAttribute(ROLE, user.getRole());
        LOGGER.info("done router path: " + router.getPagePath() + "router type: " + router.getRouteType());
        return router;
    }
}


