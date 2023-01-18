package com.epam.redkin.web.controller.command.common;

import com.epam.redkin.model.dto.MappingInfoDTO;
import com.epam.redkin.model.exception.IncorrectDataException;
import com.epam.redkin.model.service.RouteMappingService;
import com.epam.redkin.web.controller.command.Command;
import com.epam.redkin.web.listener.AppContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;

import static com.epam.redkin.util.constants.AppContextConstant.*;
import static com.epam.redkin.web.controller.Path.PAGE_ROUTE_DETAIL;

public class DetailRouteCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(DetailRouteCommand.class);
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.debug("started");
        RouteMappingService routeMappingService = AppContext.getInstance().getRouteMappingService();
        String routsId = request.getParameter(ROUTE_ID);
        String userId = request.getParameter(USER_ID);
        String departureStation = request.getParameter(DEPARTURE_STATION);
        String arrivalStation = request.getParameter(ARRIVAL_STATION);
        LocalDateTime departureDate;
        try {
            departureDate = LocalDateTime.parse(request.getParameter(DEPARTURE_DATE));
        } catch (DateTimeParseException e) {
            LOGGER.error("Incorrect data entered");
            throw new IncorrectDataException("Incorrect data entered", e);
        }
        List<MappingInfoDTO> allRouteToStationMappingListById = routeMappingService
                .getAllRoutToStationMappingListById(Integer.parseInt(routsId));
        request.setAttribute(DEPARTURE_STATION, departureStation);
        request.setAttribute(ARRIVAL_STATION, arrivalStation);
        request.setAttribute(DEPARTURE_DATE, departureDate);
        request.setAttribute(USER_ID, userId);
        request.setAttribute(ROUTE_ID, routsId);
        request.setAttribute(ROUTE_MAPPING_LIST, allRouteToStationMappingListById);
        HttpSession session = request.getSession();
        request.setAttribute(LANGUAGE, session.getAttribute(LOCALE));
        return PAGE_ROUTE_DETAIL;
    }
}
