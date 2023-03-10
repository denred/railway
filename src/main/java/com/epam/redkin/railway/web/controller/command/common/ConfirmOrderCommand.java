package com.epam.redkin.railway.web.controller.command.common;

import com.epam.redkin.railway.model.entity.*;
import com.epam.redkin.railway.model.service.*;
import com.epam.redkin.railway.model.validator.PaymentValidator;
import com.epam.redkin.railway.web.controller.command.Command;
import com.epam.redkin.railway.web.controller.command.Router;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.epam.redkin.railway.util.constants.AppContextConstant.*;
import static com.epam.redkin.railway.web.controller.Path.COMMAND_VIEW_TICKETS;
import static com.epam.redkin.railway.web.controller.Path.PAGE_CONFIRM_ORDER;

public class ConfirmOrderCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConfirmOrderCommand.class);
    private final OrderService orderService;
    private final UserService userService;

    public ConfirmOrderCommand(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("started");

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(SESSION_USER);
        user = userService.read(user.getUserId());
        String routeId = (String) session.getAttribute(ROUTE_ID);
        String trainId = (String) session.getAttribute(TRAIN_ID);
        String stationIdA = (String) session.getAttribute(ARRIVAL_STATION_ID);
        String stationIdD = (String) session.getAttribute(DEPARTURE_STATION_ID);
        String carriageId = (String) session.getAttribute(CARRIAGE_ID);
        String seatIds = (String) session.getAttribute(SEATS_ID);
        Double price = (Double) session.getAttribute(PRICE);

        String travelTime = request.getParameter(TRAVEL_TIME);
        String cardHolderName = request.getParameter("cardHoldersName");
        String expiryMonth = request.getParameter("expiryMonth");
        String expiryYear = request.getParameter("expiryYear");
        String cvvCode = request.getParameter("cvvCode");
        String balancePayment = request.getParameter("balancePayment");
        String[] cardNumber = request.getParameterValues("cardNumber");

        if (StringUtils.isNoneBlank(cardHolderName, expiryMonth, expiryYear, cvvCode) && cardNumber.length != 0){
            Payment payment = Payment.builder().cardHolderName(cardHolderName)
                    .cardNumber(String.join("", cardNumber))
                    .expiryYear(expiryYear)
                    .expiryMonth(expiryMonth)
                    .cvvCode(cvvCode)
                    .build();
            PaymentValidator paymentValidator = new PaymentValidator();
            String errorMessage = paymentValidator.isValid(payment);
            if(StringUtils.isNotBlank(errorMessage)){
                session.setAttribute(ERROR_MESSAGE, errorMessage);
                return Router.forward(PAGE_CONFIRM_ORDER);
            }
        }

        String orderUuid = orderService.addReservation(routeId, stationIdD, stationIdA, trainId, seatIds,
                travelTime, price, carriageId, user, balancePayment);

        session.setAttribute(SESSION_USER, user);
        session.removeAttribute(ERROR_MESSAGE);
        session.setAttribute("orderUuid", orderUuid);
        LOGGER.info("done");

        return Router.redirect(COMMAND_VIEW_TICKETS);
    }
}
