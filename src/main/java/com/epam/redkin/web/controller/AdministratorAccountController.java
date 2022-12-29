package com.epam.redkin.web.controller;


import com.epam.redkin.model.dto.CarriageDTO;
import com.epam.redkin.model.dto.RoutInfoDto;
import com.epam.redkin.model.entity.*;
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

@WebServlet("/administrator_account")
public class AdministratorAccountController extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(AdministratorAccountController.class);

    private OrderService orderService;
    private UserService userService;
    private StationService stationService;
    private RoutService routService;
    private TrainService trainService;
    private CarService carService;
    private RoutMappingService routMappingService;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<Order> orderList = orderService.getAllOrderList();


        /* RoutInfoDto routInfoDto = routService.getRoutById(orderList.get(i).getRoutsId());

            orderList.get(i).setRoutsId(routInfoDto.getRoutName());*/

        orderList.forEach(order -> {

            order.setRouteId(
                    routService.getRoutById(
                                    order.getRouteId())
                            .getRoutsId());
        });

        LOGGER.debug("order_list: " + orderList);

        request.setAttribute("order_list", orderList);
        List<User> userInfoList = userService.getUserInfo(Role.USER.name());

        LOGGER.debug("userInfoList: " + userInfoList);

        request.setAttribute("user_list", userInfoList);

        List<Station> stationList = stationService.getAllStationList();

        LOGGER.debug("stationList: " + stationList);

        request.setAttribute("station_list", stationList);


        List<RoutInfoDto> routList = routService.getAllRoutList();

        LOGGER.debug("routList: " + routList);

        request.setAttribute("rout_list", routList);
        List<RoutePoint> routToStationMappingList = routMappingService.getAllRoutToStationMappingList();

        LOGGER.debug("routToStationMappingList: " + routToStationMappingList);

        request.setAttribute("rout_m_list", routToStationMappingList);
        List<Train> trainList = trainService.getAllTrainList();

        LOGGER.debug("trainList: " + trainList);

        request.setAttribute("train_list", trainList);
        List<CarriageDTO> carList = carService.getAllCarList();

        LOGGER.debug("carList: " + carList);

        request.setAttribute("car_list", carList);

        request.getRequestDispatcher("WEB-INF/jsp/administratorAccount.jsp").forward(request, response);
    }

    @Override
    public void init(ServletConfig config) {
        orderService = (OrderService) config.getServletContext().getAttribute(AppContextConstant.ORDER_SERVICE);
        userService = (UserService) config.getServletContext().getAttribute(AppContextConstant.USER_SERVICE);
        stationService = (StationService) config.getServletContext().getAttribute((AppContextConstant.STATION_SERVICE));
        routService = (RoutService) config.getServletContext().getAttribute((AppContextConstant.ROUT_SERVICE));
        routMappingService = (RoutMappingService) config.getServletContext().getAttribute((AppContextConstant.ROUT_TO_STATION_MAPPING_SERVICE));
        trainService = (TrainService) config.getServletContext().getAttribute((AppContextConstant.TRAIN_SERVICE));
        carService = (CarService) config.getServletContext().getAttribute((AppContextConstant.CARS_SERVICE));
        LOGGER.trace("administrator_account Servlet init");
    }
}
