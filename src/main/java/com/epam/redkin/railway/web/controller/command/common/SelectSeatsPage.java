package com.epam.redkin.railway.web.controller.command.common;

import com.epam.redkin.railway.web.controller.Path;
import com.epam.redkin.railway.web.controller.command.Command;
import com.epam.redkin.railway.web.controller.command.Router;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SelectSeatsPage implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(SelectSeatsPage.class);

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("started");
        Router router = new Router();
        router.setRouteType(Router.RouteType.FORWARD);
        router.setPagePath(Path.PAGE_SELECT_CARRIAGE_AND_COUNT_SEATS);
        LOGGER.info("done");
        return router;
    }
}
