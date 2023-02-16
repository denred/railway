package com.epam.redkin.railway.web.controller.command.common;

import com.epam.redkin.railway.model.dto.RouteInfoDTO;
import com.epam.redkin.railway.model.dto.RouteOrderDTO;
import com.epam.redkin.railway.model.entity.Carriage;
import com.epam.redkin.railway.model.entity.Seat;
import com.epam.redkin.railway.model.service.*;
import com.epam.redkin.railway.web.controller.command.Command;
import com.epam.redkin.railway.appcontext.AppContext;
import com.epam.redkin.railway.web.controller.command.Router;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static com.epam.redkin.railway.util.constants.AppContextConstant.*;
import static com.epam.redkin.railway.web.controller.Path.COMMAND_CREATE_ORDER;
import static com.epam.redkin.railway.web.controller.Path.PAGE_CONFIRM_ORDER;

public class CreateOrderCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(CreateOrderCommand.class);

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("started");
        Router router = new Router();
        router.setRouteType(Router.RouteType.FORWARD);
        router.setPagePath(PAGE_CONFIRM_ORDER);

        RouteService routeService = AppContext.getInstance().getRouteService();
        CarriageService carriageService = AppContext.getInstance().getCarriageService();
        OrderService orderService = AppContext.getInstance().getOrderService();
        TrainService trainService = AppContext.getInstance().getTrainService();
        SeatService seatService = AppContext.getInstance().getSeatService();
        StationService stationService = AppContext.getInstance().getStationService();
        HttpSession session = request.getSession();

        String departureStationId = (String) session.getAttribute(DEPARTURE_STATION_ID);
        String arrivalStationId = (String) session.getAttribute(ARRIVAL_STATION_ID);
        String routeId = (String) session.getAttribute(ROUTE_ID);
        String trainId = (String) session.getAttribute(TRAIN_ID);
        String carriageType = (String) session.getAttribute(CARRIAGE_TYPE);
        String carriageId = (String) session.getAttribute(CARRIAGE_ID);
        LocalDateTime departureDate = (LocalDateTime) session.getAttribute(DEPARTURE_DATE);

        String[] numbers = request.getParameterValues(SEATS_ID);

        if (ArrayUtils.isNotEmpty(numbers)) {
            int countOfSeats = numbers.length;
            List<String> seatsNumber = Arrays.asList(numbers);
            RouteInfoDTO routeInfoDTO = routeService.getRouteInfoById(Integer.parseInt(routeId));
            String routName = routeInfoDTO.getRoutName();
            Carriage carriage = carriageService.getCarriageById(Integer.parseInt(carriageId));
            String carriageNumber = carriage.getNumber();
            Double price = orderService.getPrice(carriageType, countOfSeats);

            List<Seat> seatList = seatService.getSeatsByIdBatch(seatsNumber);

            String trainNumber = trainService.getTrainById(Integer.parseInt(trainId)).getNumber();
            String departure = stationService.getStationById(Integer.parseInt(departureStationId)).getStation();
            String arrival = stationService.getStationById(Integer.parseInt(arrivalStationId)).getStation();
            List<RouteOrderDTO> routeOrderDTOList = routeService.getRouteOrderDtoList(departure, arrival, departureDate, null);

            session.setAttribute(COUNT_SEATS, countOfSeats);
            session.setAttribute(ROUTE_ORDER_DTO_LIST, routeOrderDTOList);
            session.setAttribute(ROUTE_NAME, routName);
            session.setAttribute(CARRIAGE_NUMBER, carriageNumber);
            session.setAttribute(DEPARTURE_STATION, departure);
            session.setAttribute(ARRIVAL_STATION, arrival);
            session.setAttribute(PRICE, price);
            session.setAttribute(TRAIN_NUMBER, trainNumber);
            session.setAttribute(CARRIAGE_ID, carriageId);
            session.setAttribute(SEAT_LIST, seatList);
            session.setAttribute(SEATS_ID, Arrays.toString(numbers));

            router.setRouteType(Router.RouteType.REDIRECT);
            router.setPagePath(COMMAND_CREATE_ORDER);
        }
        LOGGER.info("done");
        return router;
    }
}
