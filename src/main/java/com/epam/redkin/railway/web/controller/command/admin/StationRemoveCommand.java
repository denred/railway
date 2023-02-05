package com.epam.redkin.railway.web.controller.command.admin;

import com.epam.redkin.railway.model.service.StationService;
import com.epam.redkin.railway.web.controller.Path;
import com.epam.redkin.railway.web.controller.command.Command;
import com.epam.redkin.railway.appcontext.AppContext;
import com.epam.redkin.railway.web.controller.command.Router;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StationRemoveCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(StationRemoveCommand.class);

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("started");
        Router router = new Router();
        router.setRouteType(Router.RouteType.REDIRECT);
        router.setPagePath(Path.COMMAND_INFO_STATIONS);
        StationService stationService = AppContext.getInstance().getStationService();
        String stationId = request.getParameter("station_id");
        stationService.removeStation(Integer.parseInt(stationId));
        LOGGER.info("done");
        return router;
    }
}
