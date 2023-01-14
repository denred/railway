package com.epam.redkin.web.controller.command.admin;

import com.epam.redkin.model.entity.Route;
import com.epam.redkin.model.entity.Train;
import com.epam.redkin.model.service.RouteService;
import com.epam.redkin.model.service.TrainService;
import com.epam.redkin.model.validator.RouteValidator;
import com.epam.redkin.web.controller.command.Command;
import com.epam.redkin.web.listener.AppContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static com.epam.redkin.web.controller.Path.*;

public class RouteAddCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(RouteAddCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("started");
        String forward = PAGE_ADD_ROUTE;
        RouteService routeService = AppContext.getInstance().getRouteService();
        TrainService trainService = AppContext.getInstance().getTrainService();
        String routeName = request.getParameter("rout_name");
        String routeNumber = request.getParameter("rout_number");
        String trainNumber = request.getParameter("train_number");
        if (routeName != null && routeNumber != null && trainNumber != null) {
            RouteValidator routeValidator = new RouteValidator();
            Route rout = new Route();
            rout.setRouteName(routeName);
            rout.setRouteNumber(Integer.parseInt(routeNumber));
            rout.setTrainId(Integer.parseInt(trainNumber));
            routeValidator.isValidRout(rout);
            routeService.addRout(rout);
            forward = COMMAND_INFO_ROUTE;
        }else{
            List<Train> trainList = trainService.getAllTrainList();
            request.setAttribute("trainList", trainList);
        }
        LOGGER.info("done");
        return forward;
    }
}