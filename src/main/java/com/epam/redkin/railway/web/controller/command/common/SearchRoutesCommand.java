package com.epam.redkin.railway.web.controller.command.common;

import com.epam.redkin.railway.model.dto.RoutsOrderDTO;
import com.epam.redkin.railway.model.exception.IncorrectDataException;
import com.epam.redkin.railway.model.service.RouteService;
import com.epam.redkin.railway.model.validator.SearchValidator;
import com.epam.redkin.railway.web.controller.command.Command;
import com.epam.redkin.railway.appcontext.AppContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.*;

import static com.epam.redkin.railway.util.constants.AppContextConstant.*;
import static com.epam.redkin.railway.web.controller.Path.*;

public class SearchRoutesCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(SearchRoutesCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("started");
        RouteService routeService = AppContext.getInstance().getRouteService();
        SearchValidator searchValidator = new SearchValidator();

        String departureStation = request.getParameter(DEPARTURE_STATION);
        String arrivalStation = request.getParameter(ARRIVAL_STATION);
        String startDate = request.getParameter(DEPARTURE_DATE);
        String startTime = request.getParameter(DEPARTURE_TIME);
        LocalDateTime departureDate;
        try {
            departureDate = LocalDateTime.of(LocalDate.parse(startDate), LocalTime.parse(startTime));
        } catch (DateTimeParseException e) {
            LOGGER.error("Incorrect data entered. Date or time format");
            throw new IncorrectDataException("Incorrect data entered. Date or time format", e);
        }
        searchValidator.isValidSearch(departureStation, arrivalStation);
        List<RoutsOrderDTO> routeOrderDTOList = routeService
                .getRouteListWithParameters(departureStation, arrivalStation, departureDate);
        routeService.fillAvailableSeats(routeOrderDTOList);

        request.setAttribute(ROUTE_ORDER_DTO_LIST, routeOrderDTOList);
        request.setAttribute(DEPARTURE_STATION, departureStation);
        request.setAttribute(ARRIVAL_STATION, arrivalStation);
        request.setAttribute(DEPARTURE_DATE, departureDate);
        LOGGER.info("done");
        return PAGE_SEARCH_ROUTES;
    }
}
