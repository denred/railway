package com.epam.redkin.railway.web.controller.command.common;

import com.epam.redkin.railway.model.dto.MappingInfoDTO;
import com.epam.redkin.railway.model.exception.IncorrectDataException;
import com.epam.redkin.railway.model.service.RouteMappingService;
import com.epam.redkin.railway.util.constants.AppContextConstant;
import com.epam.redkin.railway.web.controller.Path;
import com.epam.redkin.railway.web.controller.command.Command;
import com.epam.redkin.railway.appcontext.AppContext;
import com.epam.redkin.railway.web.controller.command.Router;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;

public class DetailRouteCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(DetailRouteCommand.class);
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.debug("started");
        Router router = new Router();
        router.setRouteType(Router.RouteType.FORWARD);
        router.setPagePath(Path.PAGE_ROUTE_DETAIL);
        RouteMappingService routeMappingService = AppContext.getInstance().getRouteMappingService();
        String routsId = request.getParameter(AppContextConstant.ROUTE_ID);
        String userId = request.getParameter(AppContextConstant.USER_ID);
        String departureStation = request.getParameter(AppContextConstant.DEPARTURE_STATION);
        String arrivalStation = request.getParameter(AppContextConstant.ARRIVAL_STATION);
        LocalDateTime departureDate;
        try {
            departureDate = LocalDateTime.parse(request.getParameter(AppContextConstant.DEPARTURE_DATE));
        } catch (DateTimeParseException e) {
            LOGGER.error("Incorrect data entered");
            throw new IncorrectDataException("Incorrect data entered", e);
        }
        List<MappingInfoDTO> allRouteToStationMappingListById = routeMappingService
                .getMappingInfoDtoListByRouteId(Integer.parseInt(routsId));
        request.setAttribute(AppContextConstant.DEPARTURE_STATION, departureStation);
        request.setAttribute(AppContextConstant.ARRIVAL_STATION, arrivalStation);
        request.setAttribute(AppContextConstant.DEPARTURE_DATE, departureDate);
        request.setAttribute(AppContextConstant.USER_ID, userId);
        request.setAttribute(AppContextConstant.ROUTE_ID, routsId);
        request.setAttribute(AppContextConstant.ROUTE_MAPPING_LIST, allRouteToStationMappingListById);
        HttpSession session = request.getSession();
        request.setAttribute(AppContextConstant.LANGUAGE, session.getAttribute(AppContextConstant.LOCALE));
        return router;
    }
}
