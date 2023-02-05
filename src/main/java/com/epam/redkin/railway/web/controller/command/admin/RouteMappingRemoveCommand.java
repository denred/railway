package com.epam.redkin.railway.web.controller.command.admin;

import com.epam.redkin.railway.model.service.RouteMappingService;
import com.epam.redkin.railway.web.controller.Path;
import com.epam.redkin.railway.web.controller.command.Command;
import com.epam.redkin.railway.appcontext.AppContext;
import com.epam.redkin.railway.web.controller.command.Router;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.epam.redkin.railway.util.constants.AppContextConstant.ROUTE_ID;
import static com.epam.redkin.railway.util.constants.AppContextConstant.STATION_ID;

public class RouteMappingRemoveCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(RouteMappingRemoveCommand.class);
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("started");
        Router router = new Router();
        router.setRouteType(Router.RouteType.REDIRECT);
        router.setPagePath(Path.COMMAND_ROUTE_MAPPING);
        RouteMappingService routeMappingService = AppContext.getInstance().getRouteMappingService();
        String routsId = request.getParameter(ROUTE_ID);
        String stationId = request.getParameter(STATION_ID);
        routeMappingService.removeRoutToStationMapping(Integer.parseInt(routsId), Integer.parseInt(stationId));
        LOGGER.info("done");
        return router;
    }
}
