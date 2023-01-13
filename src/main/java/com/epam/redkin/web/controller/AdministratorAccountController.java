package com.epam.redkin.web.controller;


import com.epam.redkin.model.dto.CarriageDTO;
import com.epam.redkin.model.dto.RouteInfoDTO;
import com.epam.redkin.model.entity.*;
import com.epam.redkin.model.service.*;
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

@WebServlet("/administrator_account")
public class AdministratorAccountController extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(AdministratorAccountController.class);
    private OrderService orderService;
    private UserService userService;
    private StationService stationService;
    private RouteService routeService;
    private TrainService trainService;
    private CarriageService carriageService;
    private RouteMappingService routeMappingService;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Order> orderList = orderService.getAllOrderList();
        for (Order order : orderList) {
            order.setRouteId(routeService.getRoutById(order.getRouteId()).getRoutsId());
        }
        request.setAttribute("order_list", orderList);

        List<User> userInfoList = userService.getUserInfo(Role.USER.name());
        request.setAttribute("user_list", userInfoList);

        List<Station> stationList = stationService.getAllStationList();
        request.setAttribute("station_list", stationList);

        // List<RouteInfoDTO> routeDto_list = routeService.getAllRouteList();
        int page = 1;
        int recordsPerPage = 5;
        if (request.getParameter("page") != null)
            page = Integer.parseInt(request.getParameter("page"));
        List<RouteInfoDTO> routeDtoList = routeService.getRouteListByCurrentRecordAndRecordsPerPage(
                (page - 1) * recordsPerPage,
                recordsPerPage * page);
        int noOfRecords = routeService.getRouteListSize();
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
        request.setAttribute("noOfPages", noOfPages);
        request.setAttribute("currentPage", page);
        request.setAttribute("routeDto_list", routeDtoList);

        List<RoutePoint> routToStationMappingList = routeMappingService.getAllRoutToStationMappingList();
        request.setAttribute("rout_m_list", routToStationMappingList);

        List<Train> trainList = trainService.getAllTrainList();
        request.setAttribute("train_list", trainList);

        List<CarriageDTO> carList = carriageService.getAllCarList();
        request.setAttribute("car_list", carList);

        request.getRequestDispatcher("WEB-INF/jsp/administratorAccount.jsp").forward(request, response);
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
