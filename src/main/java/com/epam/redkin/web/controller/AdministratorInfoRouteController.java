package com.epam.redkin.web.controller;


import com.epam.redkin.model.dto.RouteInfoDTO;
import com.epam.redkin.service.*;
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

@WebServlet("/administrator_info_route")
public class AdministratorInfoRouteController extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(AdministratorInfoRouteController.class);
    private OrderService orderService;
    private UserService userService;
    private StationService stationService;
    private RouteService routeService;
    private TrainService trainService;
    private CarriageService carriageService;
    private RouteMappingService routeMappingService;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int page = 1;
        int recordsPerPage = 5;
        if (request.getParameter("page") != null)
            page = Integer.parseInt(request.getParameter("page"));
        List<RouteInfoDTO> routeDtoList = routeService.getRouteListByCurrentRecordAndRecordsPerPage(
                (page - 1) * recordsPerPage,
                recordsPerPage * page);
        int noOfRecords = routeService.getRouteListSize();
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
        request.setAttribute("recordsPerPage", recordsPerPage);
        request.setAttribute("noOfPages", noOfPages);
        request.setAttribute("currentPage", page);
        request.setAttribute("routeDto_list", routeDtoList);
        request.getRequestDispatcher("WEB-INF/jsp/administratorInfoRoute.jsp").forward(request, response);
    }

    @Override
    public void init(ServletConfig config) {
        orderService = (OrderService) config.getServletContext().getAttribute(AppContextConstant.ORDER_SERVICE);
        userService = (UserService) config.getServletContext().getAttribute(AppContextConstant.USER_SERVICE);
        stationService = (StationService) config.getServletContext().getAttribute((AppContextConstant.STATION_SERVICE));
        routeService = (RouteService) config.getServletContext().getAttribute((AppContextConstant.ROUT_SERVICE));
        routeMappingService = (RouteMappingService) config.getServletContext().getAttribute((AppContextConstant.ROUT_TO_STATION_MAPPING_SERVICE));
        trainService = (TrainService) config.getServletContext().getAttribute((AppContextConstant.TRAIN_SERVICE));
        carriageService = (CarriageService) config.getServletContext().getAttribute((AppContextConstant.CARS_SERVICE));
        LOGGER.trace("administrator_account Servlet init");
    }
}
