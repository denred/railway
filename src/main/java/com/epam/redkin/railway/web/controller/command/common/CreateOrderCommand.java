package com.epam.redkin.railway.web.controller.command.common;

import com.epam.redkin.railway.model.dto.RouteInfoDTO;
import com.epam.redkin.railway.model.entity.Carriage;
import com.epam.redkin.railway.model.entity.Seat;
import com.epam.redkin.railway.model.entity.Train;
import com.epam.redkin.railway.model.entity.User;
import com.epam.redkin.railway.model.exception.IncorrectDataException;
import com.epam.redkin.railway.model.service.*;
import com.epam.redkin.railway.model.validator.SeatValidator;
import com.epam.redkin.railway.web.controller.command.Command;
import com.epam.redkin.railway.appcontext.AppContext;
import com.epam.redkin.railway.web.controller.command.Router;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;

import static com.epam.redkin.railway.util.constants.AppContextConstant.*;
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
        UserService userService = AppContext.getInstance().getUserService();
        TrainService trainService = AppContext.getInstance().getTrainService();
        SeatService seatService = AppContext.getInstance().getSeatService();

        SeatValidator seatValidator = new SeatValidator();
        String departureStation = request.getParameter("departure_station");
        String arrivalStation = request.getParameter("arrival_station");
        String departureStationId = request.getParameter("departure_station_id");
        String arrivalStationId = request.getParameter("arrival_station_id");
        String carriageType = request.getParameter(CARRIAGE_TYPE);
        LOGGER.debug("carriageType= " + carriageType);
        String trainId = request.getParameter(TRAIN_ID);
        String carId = request.getParameter("car_id");
        String countOfSeats = request.getParameter(COUNT_SEATS);
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

        RouteInfoDTO routById = routeService.getRouteInfoById(Integer.parseInt(routsId));
        String routName = routById.getRoutName();
        LOGGER.debug("Route name: " + routName);
        Carriage carriage = carriageService.getCarById(Integer.parseInt(carId));
        String carNumber = carriage.getNumber();
        Double price = orderService.getPrice(carriageType, Integer.parseInt(countOfSeats));

        User user = (User) request.getSession().getAttribute(SESSION_USER);
        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        Train train = trainService.getTrainById(Integer.parseInt(trainId));
        String trainNumber = train.getNumber();
        List<Seat> seats = seatService.getSeatsByIdBatch(seatsNumber);
        seatValidator.isValidSeat(seats, countOfSeats);

        StationService stationService = AppContext.getInstance().getStationService();
        String departure = stationService.getStationById(Integer.parseInt(departureStationId)).getStation();
        String arrival = stationService.getStationById(Integer.parseInt(arrivalStationId)).getStation();

        request.setAttribute("station1", departure);
        request.setAttribute("station2", arrival);
        request.setAttribute("travel_time", travelTime);
        request.setAttribute("price", price);
        request.setAttribute("rout_name", routName);
        request.setAttribute("car_number", carNumber);
        request.setAttribute("departure_station", departure);
        request.setAttribute("arrival_station", arrival);
        request.setAttribute("departure_date", departureDate);
        request.setAttribute("departure_station_id", departureStationId);
        request.setAttribute("arrival_station_id", arrivalStationId);
        request.setAttribute("routs_id", routsId);
        request.setAttribute("car_type", carriageType);
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
        return router;
    }
}
