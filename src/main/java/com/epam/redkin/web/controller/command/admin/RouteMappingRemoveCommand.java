package com.epam.redkin.web.controller.command.admin;

import com.epam.redkin.model.service.RouteMappingService;
import com.epam.redkin.web.controller.command.Command;
import com.epam.redkin.web.listener.AppContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.epam.redkin.web.controller.Path.COMMAND_ROUTE_MAPPING;

public class RouteMappingRemoveCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(RouteMappingRemoveCommand.class);
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("started");
        RouteMappingService routeMappingService = AppContext.getInstance().getRouteMappingService();
        String routsId = request.getParameter("routs_id");
        String stationId = request.getParameter("station_id");
        routeMappingService.removeRoutToStationMapping(Integer.parseInt(routsId), Integer.parseInt(stationId));
        LOGGER.info("done");
        return COMMAND_ROUTE_MAPPING;
    }
}
