package com.epam.redkin.web.controller.command.common;

import com.epam.redkin.model.dto.RouteInfoDTO;
import com.epam.redkin.model.entity.Carriage;
import com.epam.redkin.model.entity.Seat;
import com.epam.redkin.model.entity.Train;
import com.epam.redkin.model.entity.User;
import com.epam.redkin.model.exception.IncorrectDataException;
import com.epam.redkin.model.service.*;
import com.epam.redkin.model.validator.SeatValidator;
import com.epam.redkin.web.controller.command.Command;
import com.epam.redkin.web.listener.AppContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;

import static com.epam.redkin.web.controller.Path.PAGE_CONFIRM_ORDER;

public class CreateOrderCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(CreateOrderCommand.class);
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("started");
        RouteService routeService = AppContext.getInstance().getRouteService();
        CarriageService carriageService = AppContext.getInstance().getCarriageService();
        OrderService orderService = AppContext.getInstance().getOrderService();
        UserService userService = AppContext.getInstance().getUserService();
        TrainService trainService = AppContext.getInstance().getTrainService();
        SeatService seatService = AppContext.getInstance().getSeatService();

        SeatValidator seatValidator = new SeatValidator();
        String departureStation = request.getParameter("departure_station");
        String arrivalStation = request.getParameter("arrival_station");
        String departureStationId = request.getParameter("departure_station_id");
        String arrivalStationId = request.getParameter("arrival_station_id");
        String carType = request.getParameter("car_type");
        String trainId = request.getParameter("train_id");
        String carId = request.getParameter("car_id");
        String countOfSeats = request.getParameter("count_of_seats");
        String userId = request.getParameter("user_id");
        String station1 = request.getParameter("station1");
        String station2 = request.getParameter("station2");
        String travelTime = request.getParameter("travel_time");
        LocalDateTime departureDate;
        try {
            departureDate = LocalDateTime.parse(request.getParameter("departure_date"));
        } catch (DateTimeParseException e) {
            LOGGER.error("Incorrect data entered");
            throw new IncorrectDataException("Incorrect data entered", e);
        }

        String[] numbers = request.getParameterValues("seats_id");
        List<String> seatsNumber = Arrays.asList(request.getParameterValues("seats_id"));
        String routsId = request.getParameter("routs_id");

        RouteInfoDTO routById = routeService.getRoutById(Integer.parseInt(routsId));
        String routName = routById.getRoutName();
        Carriage car = carriageService.getCarById(Integer.parseInt(carId));
        String carNumber = car.getNumber();
        Double price = orderService.getPrice(carType, Integer.parseInt(countOfSeats));
        User user = userService.read(Integer.parseInt(userId));
        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        Train train = trainService.getTrainById(Integer.parseInt(trainId));
        String trainNumber = train.getNumber();
        List<Seat> seats = seatService.getSeatsByIdBatch(seatsNumber);
        seatValidator.isValidSeat(seats, countOfSeats);

        request.setAttribute("station1", station1);
        request.setAttribute("station2", station2);
        request.setAttribute("travel_time", travelTime);
        request.setAttribute("price", price);
        request.setAttribute("rout_name", routName);
        request.setAttribute("car_number", carNumber);
        request.setAttribute("departure_station", departureStation);
        request.setAttribute("arrival_station", arrivalStation);
        request.setAttribute("departure_date", departureDate);
        request.setAttribute("departure_station_id", departureStationId);
        request.setAttribute("arrival_station_id", arrivalStationId);
        request.setAttribute("routs_id", routsId);
        request.setAttribute("car_type", carType);
        request.setAttribute("train_id", trainId);
        request.setAttribute("user_id", userId);
        request.setAttribute("train_number", trainNumber);
        request.setAttribute("first_name", firstName);
        request.setAttribute("last_name", lastName);
        request.setAttribute("car_number", carNumber);
        request.setAttribute("count_of_seats", countOfSeats);
        request.setAttribute("seats_number", seatsNumber);
        request.setAttribute("car_id", carId);
        request.setAttribute("seats", seats);
        request.setAttribute("seats_id", Arrays.deepToString(numbers));
        LOGGER.info("done");
        return PAGE_CONFIRM_ORDER;
    }
}
