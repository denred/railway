package com.epam.redkin.railway.web.controller.command.admin;

import com.epam.redkin.railway.model.entity.Route;
import com.epam.redkin.railway.model.entity.Train;
import com.epam.redkin.railway.model.service.RouteService;
import com.epam.redkin.railway.model.service.TrainService;
import com.epam.redkin.railway.model.validator.RouteValidator;
import com.epam.redkin.railway.web.controller.Path;
import com.epam.redkin.railway.web.controller.command.Command;
import com.epam.redkin.railway.appcontext.AppContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class RouteAddCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(RouteAddCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("started");
        String forward = Path.PAGE_ADD_ROUTE;
        RouteService routeService = AppContext.getInstance().getRouteService();
        TrainService trainService = AppContext.getInstance().getTrainService();
        String routeName = request.getParameter("rout_name");
        String routeNumber = request.getParameter("rout_number");
        String trainNumber = request.getParameter("train_number");
        if (routeName != null && routeNumber != null && trainNumber != null) {
            RouteValidator routeValidator = new RouteValidator();
            Route route = Route.builder().build();
            route.setRouteName(routeName);
            route.setRouteNumber(Integer.parseInt(routeNumber));
            route.setTrainId(Integer.parseInt(trainNumber));
            routeValidator.isValidRout(route);
            routeService.addRout(route);
            forward = Path.COMMAND_INFO_ROUTE;
        }else{
            List<Train> trainList = trainService.getAllTrainList();
            request.setAttribute("trainList", trainList);
        }
        LOGGER.info("done");
        return forward;
    }
}