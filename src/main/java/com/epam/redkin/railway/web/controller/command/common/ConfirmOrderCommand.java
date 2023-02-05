package com.epam.redkin.railway.web.controller.command.common;

import com.epam.redkin.railway.model.dto.MappingInfoDTO;
import com.epam.redkin.railway.model.entity.*;
import com.epam.redkin.railway.model.exception.IncorrectDataException;
import com.epam.redkin.railway.model.validator.OrderValidator;
import com.epam.redkin.railway.model.service.*;
import com.epam.redkin.railway.util.constants.AppContextConstant;
import com.epam.redkin.railway.web.controller.Path;
import com.epam.redkin.railway.web.controller.command.Command;
import com.epam.redkin.railway.appcontext.AppContext;
import com.epam.redkin.railway.web.controller.command.Router;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class ConfirmOrderCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConfirmOrderCommand.class);

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("started");
        Router router = new Router();
        router.setRouteType(Router.RouteType.REDIRECT);
        router.setPagePath(Path.COMMAND_ORDERS);
        CarriageService carriageService = AppContext.getInstance().getCarriageService();
        TrainService trainService = AppContext.getInstance().getTrainService();
        StationService stationService = AppContext.getInstance().getStationService();
        RouteMappingService routeMappingService = AppContext.getInstance().getRouteMappingService();
        SeatService seatService = AppContext.getInstance().getSeatService();
        OrderService orderService = AppContext.getInstance().getOrderService();

        OrderValidator orderValidator = new OrderValidator();
        Order order = Order.builder().build();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(AppContextConstant.SESSION_USER);
        String routeId = request.getParameter(AppContextConstant.ROUTE_ID);
        String trainId = request.getParameter(AppContextConstant.TRAIN_ID);
        String stationIdA = request.getParameter(AppContextConstant.ARRIVAL_STATION_ID);
        String stationIdD = request.getParameter(AppContextConstant.DEPARTURE_STATION_ID);
        String carriageId = request.getParameter(AppContextConstant.CARRIAGE_ID);

        Carriage carriage = carriageService.getCarById(Integer.parseInt(carriageId));
        Train train = trainService.getTrainById(Integer.parseInt(trainId));
        Station dispatchStation = stationService.getStationById(Integer.parseInt(stationIdA));
        Station arrivalStation = stationService.getStationById(Integer.parseInt(stationIdD));
        MappingInfoDTO arrivalMapping = routeMappingService.getMappingInfo(Integer.parseInt(routeId), arrivalStation.getId());
        MappingInfoDTO dispatchMapping = routeMappingService.getMappingInfo(Integer.parseInt(routeId), dispatchStation.getId());
        String seatsId = Arrays.toString(request.getParameterValues(AppContextConstant.SEATS_ID));
        try {
            order.setCarriageType(CarriageType.valueOf(request.getParameter(AppContextConstant.CARRIAGE_TYPE)));
            order.setCountOfSeats(Integer.parseInt(request.getParameter(AppContextConstant.COUNT_SEATS)));
            order.setTravelTime(request.getParameter(AppContextConstant.TRAVEL_TIME));
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
        LOGGER.info("done");

        return router;
    }
}
