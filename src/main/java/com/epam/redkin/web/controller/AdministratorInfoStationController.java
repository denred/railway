package com.epam.redkin.web.controller;


import com.epam.redkin.model.entity.*;
import com.epam.redkin.model.service.StationService;
import com.epam.redkin.model.service.*;
import com.epam.redkin.util.constants.AppContextConstant;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

@WebServlet("/administrator_info_station")
public class AdministratorInfoStationController extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(AdministratorInfoStationController.class);
    private StationService stationService;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int page = 1;
        int recordsPerPage = 5;
        if (request.getParameter("page") != null)
            page = Integer.parseInt(request.getParameter("page"));
        List<Station> stationList = stationService.getStationListByCurrentPage(
                (page - 1) * recordsPerPage,
                recordsPerPage * page);
        int noOfRecords = stationService.getStationListSize();
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
        request.setAttribute("recordsPerPage", recordsPerPage);
        request.setAttribute("station_list", stationList);
        request.setAttribute("noOfPages", noOfPages);
        request.setAttribute("currentPage", page);
        request.getRequestDispatcher("WEB-INF/jsp/administratorInfoStation.jsp").forward(request, response);
    }

    @Override
    public void init(ServletConfig config) {
        stationService = (StationService) config.getServletContext().getAttribute((AppContextConstant.STATION_SERVICE));
        LOGGER.debug("administrator_info_station Servlet init");
    }
}
