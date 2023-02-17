package com.epam.redkin.railway.web.controller.command.admin;

import com.epam.redkin.railway.web.controller.Path;
import com.epam.redkin.railway.web.controller.command.Command;
import com.epam.redkin.railway.web.controller.command.Router;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CarriageSetPageCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(CarriageSetPageCommand.class);

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("started");
        Router router = new Router();
        router.setPagePath(Path.PAGE_ADMIN_SET_CARRIAGE);
        router.setRouteType(Router.RouteType.FORWARD);
        LOGGER.info("done");
        return router;
    }
}
