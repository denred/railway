package com.epam.redkin.web.controller;


import com.epam.redkin.service.StationService;
import com.epam.redkin.util.constants.AppContextConstant;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@WebServlet("/station_delete")
public class StationDeleteController extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(StationDeleteController.class);
    private StationService stationService;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String stationId = request.getParameter("station");
        stationService.removeStation(Integer.parseInt(stationId));
        response.sendRedirect("administrator_info_station");
    }

    @Override
    public void init(ServletConfig config) {
        stationService = (StationService) config.getServletContext().getAttribute(AppContextConstant.STATION_SERVICE);
        LOGGER.trace("station_delete Servlet init");
    }
}
