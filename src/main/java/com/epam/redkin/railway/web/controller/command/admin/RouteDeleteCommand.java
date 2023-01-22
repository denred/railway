package com.epam.redkin.railway.web.controller.command.admin;

import com.epam.redkin.railway.model.service.RouteService;
import com.epam.redkin.railway.web.controller.Path;
import com.epam.redkin.railway.web.controller.command.Command;
import com.epam.redkin.railway.appcontext.AppContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RouteDeleteCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(RouteDeleteCommand.class);
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("started");
        RouteService routeService = AppContext.getInstance().getRouteService();
        String routeId = request.getParameter("routs_id");
        routeService.removeRout(Integer.parseInt(routeId));
        LOGGER.info("done");
        return Path.COMMAND_INFO_ROUTE;
    }
}
