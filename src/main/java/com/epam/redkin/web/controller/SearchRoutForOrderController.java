package com.epam.redkin.web.controller;


import com.epam.redkin.model.dto.RoutsOrderDto;
import com.epam.redkin.model.entity.CarriageType;
import com.epam.redkin.model.exception.IncorrectDataException;
import com.epam.redkin.service.RoutService;
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
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

@WebServlet("/search_rout_for_order")
public class SearchRoutForOrderController extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(SearchRoutForOrderController.class);

    private RoutService routService;

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

        List<CarriageType> carTypeList = new ArrayList<>(EnumSet.allOf(CarriageType.class));
        searchValidator.isValidSearch(departureStation, arrivalStation);
        request.setAttribute("carTypeList", carTypeList);
        List<RoutsOrderDto> routList = routService.getRouteListWithParameters(departureStation, arrivalStation, departureDate);

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
        routService = (RoutService) config.getServletContext().getAttribute((AppContextConstant.ROUT_SERVICE));
        LOGGER.trace("search_rout_for_order Servlet init");
    }
}
