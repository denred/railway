package com.epam.redkin.railway.web.controller.command.common;

import com.epam.redkin.railway.model.entity.Seat;
import com.epam.redkin.railway.model.exception.IncorrectDataException;
import com.epam.redkin.railway.model.service.SeatService;
import com.epam.redkin.railway.model.validator.SeatValidator;
import com.epam.redkin.railway.web.controller.Path;
import com.epam.redkin.railway.web.controller.command.Command;
import com.epam.redkin.railway.appcontext.AppContext;
import com.epam.redkin.railway.web.controller.command.Router;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;

import static com.epam.redkin.railway.util.constants.AppContextConstant.CARRIAGE_TYPE;
import static com.epam.redkin.railway.util.constants.AppContextConstant.TRAIN_ID;

public class SelectSeatsCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(SelectSeatsCommand.class);
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("started");
        Router router = new Router();
        router.setRouteType(Router.RouteType.FORWARD);
        router.setPagePath(Path.PAGE_SELECT_SEATS_NUMBER);
        SeatService seatService = AppContext.getInstance().getSeatService();
        SeatValidator seatValidator = new SeatValidator();
        String departureStation = request.getParameter("departure_station");
        String arrivalStation = request.getParameter("arrival_station");
        String departureStationId = request.getParameter("departure_station_id");
        String arrivalStationId = request.getParameter("arrival_station_id");
        String carType = request.getParameter(CARRIAGE_TYPE);
        String trainId = request.getParameter(TRAIN_ID);
        String userId = request.getParameter("user_id");
        String carId = request.getParameter("car_id");
        String station1 = request.getParameter("station1");
        String station2 = request.getParameter("station2");
        String travelTime = request.getParameter("travel_time");
        String countOfSeats = request.getParameter("count_of_seats");
        countOfSeats = seatValidator.checkCountSeats(countOfSeats, seatService.getCountSeat(Integer.parseInt(carId)));
        seatValidator.isValidSeat(countOfSeats);
        LocalDateTime departureDate;
        try {
            departureDate = LocalDateTime.parse(request.getParameter("departure_date"));
        } catch (DateTimeParseException e) {
            LOGGER.error("Incorrect data entered", e);
            throw new IncorrectDataException("Incorrect data entered", e);
        }
        String routsId = request.getParameter("routs_id");
        List<Seat> seatList = seatService.getSeatByCarId(Integer.parseInt(carId));
        request.setAttribute("station1",station1);
        request.setAttribute("station2",station2);
        request.setAttribute("travel_time",travelTime);
        request.setAttribute("departure_station", departureStation);
        request.setAttribute("arrival_station", arrivalStation);
        request.setAttribute("departure_date", departureDate);
        request.setAttribute("departure_station_id", departureStationId);
        request.setAttribute("arrival_station_id", arrivalStationId);
        request.setAttribute("routs_id", routsId);
        request.setAttribute("car_type", carType);
        request.setAttribute(TRAIN_ID, trainId);
        request.setAttribute("user_id", userId);
        request.setAttribute("car_id", carId);
        request.setAttribute("count_of_seats", countOfSeats);
        request.setAttribute("seat_list", seatList);
        LOGGER.info("done");
        return router;
    }
}
