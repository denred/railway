package com.epam.redkin.web.controller;


import com.epam.redkin.model.entity.Station;
import com.epam.redkin.model.service.StationService;
import com.epam.redkin.util.constants.AppContextConstant;
import com.epam.redkin.model.validator.StationValidator;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@WebServlet("/administrator_add_station")
public class AdministratorAddStationController extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(AdministratorAddStationController.class);

    private StationService stationService;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        StationValidator stationValidator = new StationValidator();
        Station station = new Station();
        station.setStation(request.getParameter("station_station"));
        stationValidator.isValidStation(station);
        stationService.addStation(station);
        response.sendRedirect("administrator_info_station");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("WEB-INF/jsp/administratorAddStation.jsp").forward(request, response);
    }

    public void init(ServletConfig config) {
        stationService = (StationService) config.getServletContext().getAttribute(AppContextConstant.STATION_SERVICE);
        LOGGER.trace("administrator_add_station Servlet init");

    }
}
