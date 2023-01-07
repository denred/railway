package com.epam.redkin.web.controller;


import com.epam.redkin.model.dto.RouteInfoDTO;
import com.epam.redkin.model.entity.Carriage;
import com.epam.redkin.model.exception.IncorrectDataException;
import com.epam.redkin.service.CarService;
import com.epam.redkin.service.RouteService;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;

@WebServlet("/select_cars_and_seats_for_order")
public class SelectCarAndCountSeatsForOrderController extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(SelectCarAndCountSeatsForOrderController.class);
    private RouteService routeService;
    private CarService carService;


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String departureStation = request.getParameter("departure_station");
        String departureStationId = request.getParameter("departure_station_id");
        String arrivalStation = request.getParameter("arrival_station");
        String arrivalStationId = request.getParameter("arrival_station_id");
        String carType = request.getParameter("car_type");
        String trainId = request.getParameter("train_id");
        String userId = request.getParameter("user_id");
        String station1 = request.getParameter("station1");
        String station2 = request.getParameter("station2");
        String travelTime = request.getParameter("travel_time");
        request.setAttribute("station1",station1);
        request.setAttribute("station2",station2);
        request.setAttribute("travel_time",travelTime);
        LocalDateTime departureDate;
        try {
            departureDate = LocalDateTime.parse(request.getParameter("departure_date"));
        } catch (DateTimeParseException e) {
           LOGGER.error("Incorrect data entered");
            throw new IncorrectDataException("Incorrect data entered", e);
        }
        String routsId = request.getParameter("routs_id");
        request.setAttribute("departure_station", departureStation);
        request.setAttribute("departure_station_id", departureStationId);
        request.setAttribute("arrival_station", arrivalStation);
        request.setAttribute("arrival_station_id", arrivalStationId);
        request.setAttribute("departure_date", departureDate);
        request.setAttribute("routs_id", routsId);
        request.setAttribute("car_type", carType);
        request.setAttribute("user_id", userId);
        request.setAttribute("train_id", trainId);
        RouteInfoDTO routeInfoDto = routeService.getRoutById(Integer.parseInt(routsId));
        List<Carriage> carList = carService.getCarByTrainIdAndCarType(routeInfoDto.getTrainId(), carType);
        request.setAttribute("car_list", carList);

        request.getRequestDispatcher("WEB-INF/jsp/selectCarAndCountSeatsForOrder.jsp").forward(request, response);
    }

    public void init(ServletConfig config) {
        routeService = (RouteService) config.getServletContext().getAttribute((AppContextConstant.ROUT_SERVICE));
        carService = (CarService) config.getServletContext().getAttribute((AppContextConstant.CARS_SERVICE));
       // LOGGER.trace("select_cars_and_seats_for_order Servlet init");
    }
}
