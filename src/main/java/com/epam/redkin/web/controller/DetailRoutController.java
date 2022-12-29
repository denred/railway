package com.epam.redkin.web.controller;


import com.epam.redkin.model.dto.MappingInfoDto;
import com.epam.redkin.model.exception.IncorrectDataException;
import com.epam.redkin.service.RoutMappingService;
import com.epam.redkin.util.constants.AppContextConstant;
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
import java.util.List;

@WebServlet("/detail_rout")
public class DetailRoutController extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(DetailRoutController.class);

    private RoutMappingService routMappingService;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String routsId = request.getParameter("routs_id");
        String userId = request.getParameter("user_id");
        String departureStation = request.getParameter("departure_station");
        String arrivalStation = request.getParameter("arrival_station");
        LocalDateTime departureDate;
        try {
            departureDate = LocalDateTime.parse(request.getParameter("departure_date"));
        } catch (DateTimeParseException e) {
            LOGGER.error("Incorrect data entered");
            throw new IncorrectDataException("Incorrect data entered", e);
        }
        request.setAttribute("departure_station", departureStation);
        request.setAttribute("arrival_station", arrivalStation);
        request.setAttribute("departure_date", departureDate);
        request.setAttribute("user_id", userId);
        request.setAttribute("routs_id", routsId);
        List<MappingInfoDto> allRouteToStationMappingListById = routMappingService.getAllRoutToStationMappingListById(Integer.parseInt(routsId));


        LOGGER.debug("===========ROUTE====DETAIL======");
        LOGGER.debug("AllRoutToStationMappingListById: " + allRouteToStationMappingListById);
        LOGGER.debug("================================");


        request.setAttribute("rout_m_list", allRouteToStationMappingListById);
        HttpSession session = request.getSession();
        request.setAttribute("language", session.getAttribute(AppContextConstant.LOCALE));
        request.getRequestDispatcher("WEB-INF/jsp/detailRout.jsp").forward(request, response);
    }

    @Override
    public void init(ServletConfig config) {
        routMappingService = (RoutMappingService) config.getServletContext().getAttribute((AppContextConstant.ROUT_TO_STATION_MAPPING_SERVICE));
        LOGGER.trace("detail_rout Servlet init");

    }
}