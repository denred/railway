package com.epam.redkin.web.controller;


import com.epam.redkin.model.dto.RoutsOrderDTO;
import com.epam.redkin.model.entity.CarriageType;
import com.epam.redkin.model.exception.IncorrectDataException;
import com.epam.redkin.service.RouteService;
import com.epam.redkin.service.SeatService;
import com.epam.redkin.util.constants.AppContextConstant;
import com.epam.redkin.validator.SearchValidator;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.*;

@WebServlet("/search_rout_for_order")
public class SearchRoutForOrderController extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(SearchRoutForOrderController.class);
    private RouteService routeService;
    private SeatService seatService;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
        request.setAttribute("language", session.getAttribute(AppContextConstant.LOCALE));
        request.getRequestDispatcher("WEB-INF/jsp/searchRoutForOrder.jsp").forward(request, response);
    }

    public void init(ServletConfig config) {
        routeService = (RouteService) config.getServletContext().getAttribute((AppContextConstant.ROUT_SERVICE));
        seatService = (SeatService) config.getServletContext().getAttribute((AppContextConstant.SEAT_SERVICE));
        LOGGER.trace("search_rout_for_order Servlet init");
    }
}
