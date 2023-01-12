package com.epam.redkin.web.controller.command.common;

import com.epam.redkin.model.dto.RoutsOrderDTO;
import com.epam.redkin.model.entity.CarriageType;
import com.epam.redkin.model.exception.IncorrectDataException;
import com.epam.redkin.service.RouteService;
import com.epam.redkin.service.SeatService;
import com.epam.redkin.validator.SearchValidator;
import com.epam.redkin.web.controller.command.Command;
import com.epam.redkin.web.listener.AppContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.*;

import static com.epam.redkin.web.controller.Path.LOCALE;
import static com.epam.redkin.web.controller.Path.PAGE_SEARCH_ROUTES;

public class SearchRoutesCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(SearchRoutesCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        RouteService routeService = AppContext.getInstance().getRouteService();
        SeatService seatService = AppContext.getInstance().getSeatService();
        SearchValidator searchValidator = new SearchValidator();

        String departureStation = request.getParameter("departure_station");
        String arrivalStation = request.getParameter("arrival_station");
        String userId = request.getParameter("user_id");
        LocalDateTime departureDate;
        try {
            departureDate = LocalDateTime.parse(request.getParameter("departure_date"));
        } catch (DateTimeParseException e) {
            LOGGER.error("Incorrect data entered");
            throw new IncorrectDataException("Incorrect data entered", e);
        }
        searchValidator.isValidSearch(departureStation, arrivalStation);
        List<RoutsOrderDTO> routList = routeService.getRouteListWithParameters(departureStation, arrivalStation, departureDate);
        List<CarriageType> carriageTypeList = new ArrayList<>(EnumSet.allOf(CarriageType.class));
        Map<CarriageType, Integer> freeSeatsCount = new HashMap<>();
        Map<CarriageType, Double> freeSeatsPrice = new HashMap<>();
        carriageTypeList.forEach(type -> {
            freeSeatsCount.put(type, seatService.getCountSeatByCarType(routList.get(0).getTrainId(), type));
            freeSeatsPrice.put(type, type.getPrice());
        });
        request.setAttribute("carTypeList", carriageTypeList);
        request.setAttribute("seatsCount", freeSeatsCount);
        request.setAttribute("seatsPrice", freeSeatsPrice);
        request.setAttribute("rout_list", routList);
        request.setAttribute("user_id", userId);
        request.setAttribute("departure_station", departureStation);
        request.setAttribute("arrival_station", arrivalStation);
        request.setAttribute("departure_date", departureDate);
        HttpSession session = request.getSession();
        request.setAttribute("lang", session.getAttribute(LOCALE));

        return PAGE_SEARCH_ROUTES;
    }
}
