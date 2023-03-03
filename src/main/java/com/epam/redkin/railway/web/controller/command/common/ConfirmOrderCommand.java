package com.epam.redkin.railway.web.controller.command.common;

import com.epam.redkin.railway.model.entity.*;
import com.epam.redkin.railway.model.service.*;
import com.epam.redkin.railway.web.controller.Path;
import com.epam.redkin.railway.web.controller.command.Command;
import com.epam.redkin.railway.web.controller.command.Router;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.epam.redkin.railway.util.constants.AppContextConstant.*;

public class ConfirmOrderCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConfirmOrderCommand.class);
    private final OrderService orderService;

    public ConfirmOrderCommand(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("started");

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(SESSION_USER);
        String routeId = request.getParameter(ROUTE_ID);
        String trainId = request.getParameter(TRAIN_ID);
        String stationIdA = request.getParameter(ARRIVAL_STATION_ID);
        String stationIdD = request.getParameter(DEPARTURE_STATION_ID);
        String carriageId = request.getParameter(CARRIAGE_ID);
        String[] seatIds = request.getParameterValues(SEATS_ID);
        String travelTime = request.getParameter(TRAVEL_TIME);
        Double price = (Double) session.getAttribute(PRICE);

        orderService.addReservation(routeId, stationIdD, stationIdA, trainId, seatIds,
                travelTime, price, carriageId, user);

        LOGGER.info("done");

        return Router.redirect(Path.COMMAND_ORDERS);
    }
}
