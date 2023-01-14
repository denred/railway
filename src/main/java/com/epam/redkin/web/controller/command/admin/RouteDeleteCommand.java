package com.epam.redkin.web.controller.command.admin;

import com.epam.redkin.model.service.RouteService;
import com.epam.redkin.web.controller.command.Command;
import com.epam.redkin.web.listener.AppContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.epam.redkin.web.controller.Path.COMMAND_INFO_ROUTE;

public class RouteDeleteCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(RouteDeleteCommand.class);
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("started");
        RouteService routeService = AppContext.getInstance().getRouteService();
        String routeId = request.getParameter("routs_id");
        routeService.removeRout(Integer.parseInt(routeId));
        LOGGER.info("done");
        return COMMAND_INFO_ROUTE;
    }
}
