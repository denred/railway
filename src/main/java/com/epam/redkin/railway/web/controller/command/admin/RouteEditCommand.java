package com.epam.redkin.railway.web.controller.command.admin;

import com.epam.redkin.railway.model.dto.RouteInfoDTO;
import com.epam.redkin.railway.model.entity.Route;
import com.epam.redkin.railway.model.entity.Train;
import com.epam.redkin.railway.model.service.RouteService;
import com.epam.redkin.railway.model.service.TrainService;
import com.epam.redkin.railway.model.validator.RouteValidator;
import com.epam.redkin.railway.web.controller.Path;
import com.epam.redkin.railway.web.controller.command.Command;
import com.epam.redkin.railway.appcontext.AppContext;
import com.epam.redkin.railway.web.controller.command.Router;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static com.epam.redkin.railway.util.constants.AppContextConstant.*;

public class RouteEditCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(RouteEditCommand.class);

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("started");
        Router router = new Router();
        router.setRouteType(Router.RouteType.FORWARD);
        router.setPagePath(Path.PAGE_EDIT_ROUTE);
        RouteService routeService = AppContext.getInstance().getRouteService();
        TrainService trainService = AppContext.getInstance().getTrainService();
        String routeId = request.getParameter(ROUTE_ID);
        String routeName = request.getParameter(ROUTE_NAME);
        String routeNumber = request.getParameter(ROUTE_NUMBER);
        String trainNumber = request.getParameter(TRAIN_NUMBER);
        if (StringUtils.isNoneBlank(routeId, routeNumber, routeName, trainNumber)) {
            RouteValidator routeValidator = new RouteValidator();
            Route route = Route.builder()
                    .routeId(Integer.parseInt(routeId))
                    .routeName(routeName)
                    .routeNumber(Integer.parseInt(routeNumber))
                    .trainId(Integer.parseInt(trainNumber))
                    .build();

            routeValidator.isValidRoute(route);
            routeService.updateRoute(route);
            router.setRouteType(Router.RouteType.REDIRECT);
            router.setPagePath(Path.COMMAND_INFO_ROUTE);
        } else if (StringUtils.isNoneBlank(routeNumber, routeName, trainNumber)) {
            RouteValidator routeValidator = new RouteValidator();
            Route route = Route.builder()
                    .routeName(routeName)
                    .routeNumber(Integer.parseInt(routeNumber))
                    .trainId(Integer.parseInt(trainNumber))
                    .build();
            routeValidator.isValidRoute(route);
            routeService.addRoute(route);
            router.setRouteType(Router.RouteType.REDIRECT);
            router.setPagePath(Path.COMMAND_INFO_ROUTE);
        } else {
            if (StringUtils.isNoneBlank(routeId)) {
                RouteInfoDTO routeInfoDTO = routeService.getRouteInfoById(Integer.parseInt(routeId));
                request.setAttribute(CURRENT_ROUTE, routeInfoDTO);
            }
            List<Train> trainList = trainService.getTrainList();
            request.setAttribute(TRAIN_LIST, trainList);
        }
        LOGGER.info("done");
        return router;
    }
}
