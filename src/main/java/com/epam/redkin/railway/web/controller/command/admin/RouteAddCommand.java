package com.epam.redkin.railway.web.controller.command.admin;

import com.epam.redkin.railway.model.entity.Route;
import com.epam.redkin.railway.model.entity.Train;
import com.epam.redkin.railway.model.service.RouteService;
import com.epam.redkin.railway.model.service.TrainService;
import com.epam.redkin.railway.model.validator.RouteValidator;
import com.epam.redkin.railway.web.controller.Path;
import com.epam.redkin.railway.web.controller.command.Command;
import com.epam.redkin.railway.web.controller.command.Router;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static com.epam.redkin.railway.util.constants.AppContextConstant.*;

public class RouteAddCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(RouteAddCommand.class);
    private final RouteService routeService;
    private final TrainService trainService;

    public RouteAddCommand(RouteService routeService, TrainService trainService) {
        this.routeService = routeService;
        this.trainService = trainService;
    }

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("Started command execution");

        String routeName = request.getParameter(ROUTE_NAME);
        String routeNumber = request.getParameter(ROUTE_NUMBER);
        String trainNumber = request.getParameter(TRAIN_NUMBER);

        if (StringUtils.isNoneBlank(routeName, routeNumber, trainNumber)) {
            RouteValidator routeValidator = new RouteValidator();
            Route route = Route.builder()
                    .routeName(routeName)
                    .routeNumber(Integer.parseInt(routeNumber))
                    .trainId(Integer.parseInt(trainNumber))
                    .build();
            routeValidator.isValidRoute(route);
            routeService.addRoute(route);

            LOGGER.info("Command execution done");
            return Router.redirect(Path.COMMAND_INFO_ROUTE);
        } else {
            List<Train> trainList = trainService.getTrainList();
            request.setAttribute(TRAIN_LIST, trainList);
        }

        LOGGER.info("Loaded page: {}", Path.PAGE_ADD_ROUTE);
        return Router.forward(Path.PAGE_ADD_ROUTE);
    }
}