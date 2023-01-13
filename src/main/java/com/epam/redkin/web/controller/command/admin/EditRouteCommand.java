package com.epam.redkin.web.controller.command.admin;

import com.epam.redkin.model.dto.RouteInfoDTO;
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

import static com.epam.redkin.web.controller.Path.COMMAND_INFO_ROUTE;
import static com.epam.redkin.web.controller.Path.PAGE_EDIT_ROUTE;

public class EditRouteCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(EditRouteCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("started");
        RouteService routeService = AppContext.getInstance().getRouteService();
        TrainService trainService = AppContext.getInstance().getTrainService();
        String forward = PAGE_EDIT_ROUTE;
        String routeId = request.getParameter("routs_id");
        String routeName = request.getParameter("rout_name");
        String routeNumber = request.getParameter("rout_number");
        String trainNumber = request.getParameter("train_number");
        if (routeId != null && routeName != null && routeNumber != null && trainNumber != null) {
            RouteValidator routeValidator = new RouteValidator();
            Route route = new Route();
            route.setRouteId(Integer.parseInt(routeId));
            route.setRouteName(routeName);
            route.setRouteNumber(Integer.parseInt(routeNumber));
            route.setTrainId(Integer.parseInt(trainNumber));
            routeValidator.isValidRout(route);
            routeService.updateRoute(route);
            forward = COMMAND_INFO_ROUTE;
        }else{
            RouteInfoDTO rout = routeService.getRoutById(Integer.parseInt(routeId));
            request.setAttribute("current_rout", rout);
            List<Train> trainList = trainService.getAllTrainList();
            request.setAttribute("trainList", trainList);
        }
        LOGGER.info("done");
        return forward;
    }
}
