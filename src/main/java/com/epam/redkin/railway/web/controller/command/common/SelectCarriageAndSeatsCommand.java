package com.epam.redkin.railway.web.controller.command.common;

import com.epam.redkin.railway.model.dto.RouteInfoDTO;
import com.epam.redkin.railway.model.entity.Carriage;
import com.epam.redkin.railway.model.service.CarriageService;
import com.epam.redkin.railway.model.service.RouteService;
import com.epam.redkin.railway.web.controller.Path;
import com.epam.redkin.railway.web.controller.command.Command;
import com.epam.redkin.railway.appcontext.AppContext;
import com.epam.redkin.railway.web.controller.command.Router;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

import static com.epam.redkin.railway.util.constants.AppContextConstant.*;

public class SelectCarriageAndSeatsCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(SelectCarriageAndSeatsCommand.class);

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("started");
        Router router = new Router();
        router.setRouteType(Router.RouteType.FORWARD);
        router.setPagePath(Path.PAGE_SELECT_CARRIAGE_AND_COUNT_SEATS);
        RouteService routeService = AppContext.getInstance().getRouteService();
        CarriageService carriageService = AppContext.getInstance().getCarriageService();
        HttpSession session = request.getSession();
        String carriageType = request.getParameter(CARRIAGE_TYPE);
        String departure_station_id = request.getParameter(DEPARTURE_STATION_ID);
        String arrival_station_id = request.getParameter(ARRIVAL_STATION_ID);
        String routeId = (String) session.getAttribute(ROUTE_ID);
        if (StringUtils.isNoneBlank(carriageType, routeId, departure_station_id, arrival_station_id)) {
            RouteInfoDTO routeInfoDto = routeService.getRouteInfoById(Integer.parseInt(routeId));
            List<Carriage> carriageList = carriageService
                    .getCarByTrainIdAndCarType(routeInfoDto.getTrainId(), carriageType)
                    .stream()
                    .distinct()
                    .collect(Collectors.toList());
            session.setAttribute(CARRIAGE_TYPE, carriageType);
            session.setAttribute(CARRIAGE_DTO_LIST, carriageList);
            session.setAttribute(TRAIN_ID, routeInfoDto.getTrainId());
            session.setAttribute(DEPARTURE_STATION_ID, departure_station_id);
            session.setAttribute(ARRIVAL_STATION_ID, arrival_station_id);
            router.setRouteType(Router.RouteType.REDIRECT);
            router.setPagePath(Path.COMMAND_SELECT_CARRIAGE_AND_COUNT_SEATS);
        }
        return router;
    }
}
