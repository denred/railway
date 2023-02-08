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
import jakarta.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;

import static com.epam.redkin.railway.util.constants.AppContextConstant.*;

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
        HttpSession session = request.getSession();
        String carriageId = request.getParameter(CARRIAGE_ID);
        String countOfSeats = request.getParameter(COUNT_SEATS);
        if (StringUtils.isNoneBlank(carriageId, countOfSeats)) {
            countOfSeats = seatValidator.checkCountSeats(countOfSeats, seatService.getCountSeat(Integer.parseInt(carriageId)));
            seatValidator.isValidSeat(countOfSeats);
            List<Seat> seatList = seatService.getSeatByCarId(Integer.parseInt(carriageId));
            session.setAttribute(CARRIAGE_ID, carriageId);
            session.setAttribute(COUNT_SEATS, countOfSeats);
            session.setAttribute(SEAT_LIST, seatList);
            router.setRouteType(Router.RouteType.REDIRECT);
            router.setPagePath(Path.COMMAND_SELECT_SEATS_NUMBER);
        }
        LOGGER.info("done");
        return router;
    }
}
