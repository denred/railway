package com.epam.redkin.web.controller.command.common;

import com.epam.redkin.model.dto.MappingInfoDTO;
import com.epam.redkin.model.entity.*;
import com.epam.redkin.model.exception.IncorrectDataException;
import com.epam.redkin.model.service.*;
import com.epam.redkin.model.validator.OrderValidator;
import com.epam.redkin.web.controller.command.Command;
import com.epam.redkin.web.listener.AppContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static com.epam.redkin.util.constants.AppContextConstant.*;
import static com.epam.redkin.web.controller.Path.*;

public class ConfirmOrderCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConfirmOrderCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("started");
        CarriageService carriageService = AppContext.getInstance().getCarriageService();
        TrainService trainService = AppContext.getInstance().getTrainService();
        StationService stationService = AppContext.getInstance().getStationService();
        RouteMappingService routeMappingService = AppContext.getInstance().getRouteMappingService();
        SeatService seatService = AppContext.getInstance().getSeatService();
        OrderService orderService = AppContext.getInstance().getOrderService();

        OrderValidator orderValidator = new OrderValidator();
        Order order = new Order();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(SESSION_USER);
        String routeId = request.getParameter(ROUTE_ID);
        String trainId = request.getParameter(TRAIN_ID);
        String stationIdA = request.getParameter(ARRIVAL_STATION_ID);
        String stationIdD = request.getParameter(DEPARTURE_STATION_ID);
        String carriageId = request.getParameter(CARRIAGE_ID);
        Carriage carriage = carriageService.getCarById(Integer.parseInt(carriageId));
        Train train = trainService.getTrainById(Integer.parseInt(trainId));
        Station dispatchStation = stationService.getStationById(Integer.parseInt(stationIdA));
        Station arrivalStation = stationService.getStationById(Integer.parseInt(stationIdD));
        MappingInfoDTO arrivalMapping = routeMappingService.getMappingInfo(Integer.parseInt(routeId), arrivalStation.getId());
        MappingInfoDTO dispatchMapping = routeMappingService.getMappingInfo(Integer.parseInt(routeId), dispatchStation.getId());
        String seatsId = Arrays.toString(request.getParameterValues(SEATS_ID));
        try {
            order.setCarriageType(CarriageType.valueOf(request.getParameter(CARRIAGE_TYPE)));
            order.setCountOfSeats(Integer.parseInt(request.getParameter(COUNT_SEATS)));
            order.setTravelTime(request.getParameter(TRAVEL_TIME));
            List<String> seatIdList = seatService.getSeatsId(seatsId);
            List<Seat> seats = seatService.getSeatsByIdBatch(seatIdList);
            StringBuilder sb = new StringBuilder();
            String number = "";
            for (int i = 0; i <= seats.size() - 1; i++) {
                number = sb.append(seats.get(i).getSeatNumber()).append(" ").toString();
            }
            order.setRouteId(Integer.parseInt(routeId));
            order.setArrivalDate(arrivalMapping.getStationDispatchData());
            order.setDispatchDate(dispatchMapping.getStationArrivalDate());
            order.setUser(user);
            order.setTrainNumber(train.getNumber());
            order.setCarriageNumber(carriage.getNumber());
            order.setOrderDate(LocalDateTime.now());
            order.setOrderStatus(OrderStatus.PROCESSING);
            order.setArrivalStation(dispatchStation.getStation());
            order.setDispatchStation(arrivalStation.getStation());
            order.setSeatNumber(number);

            sb = new StringBuilder();
            String id = "";
            for (Seat seat : seats) {
                id = sb.append(seat.getId()).append(" ").toString();
            }
            order.setSeatsId(id);
            orderService.addOrder(order, Integer.parseInt(routeId), seats);
        } catch (IllegalArgumentException | ArithmeticException | DateTimeException e) {
            LOGGER.error(e.getMessage());
            throw new IncorrectDataException("Incorrect data entered", e);
        }
        orderValidator.isValidOrder(order);
        int userId = user.getUserId();
        request.setAttribute(USER_ID, userId);
        request.setAttribute(LANGUAGE, session.getAttribute(LOCALE));
        LOGGER.info("done");

        return COMMAND_ORDERS;
    }
}
