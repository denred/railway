package com.epam.redkin.railway.web.controller.command.common;

import com.epam.redkin.railway.model.dto.BookingDTO;
import com.epam.redkin.railway.model.dto.MappingInfoDTO;
import com.epam.redkin.railway.model.dto.ReservationDTO;
import com.epam.redkin.railway.model.entity.*;
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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.epam.redkin.railway.util.constants.AppContextConstant.PRICE;

public class ConfirmOrderCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConfirmOrderCommand.class);

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("started");

        StationService stationService = AppContext.getInstance().getStationService();
        RouteMappingService routeMappingService = AppContext.getInstance().getRouteMappingService();
        OrderService orderService = AppContext.getInstance().getOrderService();

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(AppContextConstant.SESSION_USER);
        String routeId = request.getParameter(AppContextConstant.ROUTE_ID);
        String trainId = request.getParameter(AppContextConstant.TRAIN_ID);
        String stationIdA = request.getParameter(AppContextConstant.ARRIVAL_STATION_ID);
        String stationIdD = request.getParameter(AppContextConstant.DEPARTURE_STATION_ID);
        String carriageId = request.getParameter(AppContextConstant.CARRIAGE_ID);
        String[] seatIds = request.getParameterValues(AppContextConstant.SEATS_ID)[0].split(",");

        /*  add reservation */
        List<ReservationDTO> reservations = new ArrayList<>();
        ReservationDTO reservation = ReservationDTO.builder().build();

        List<MappingInfoDTO> routeStations = routeMappingService.getRouteStations(Integer.parseInt(routeId), Integer.parseInt(stationIdA), Integer.parseInt(stationIdD));
        for (MappingInfoDTO routeStation : routeStations) {
            for (String seatId : seatIds) {
                reservation.setStatus("reserved");
                reservation.setStationId(Integer.parseInt(routeStation.getStationId()));
                reservation.setSeatId(Integer.parseInt(seatId));
                reservation.setTrainId(Integer.parseInt(trainId));
                reservation.setRouteId(Integer.parseInt(routeId));
                reservation.setSequenceNumber(routeStation.getOrder());
            }
            reservations.add(reservation);
        }

        orderService.addReservation(reservations);

        /* create booking */
        Station dispatchStation = stationService.getStationById(Integer.parseInt(stationIdA));
        Station arrivalStation = stationService.getStationById(Integer.parseInt(stationIdD));
        MappingInfoDTO arrivalMapping = routeMappingService.getMappingInfo(Integer.parseInt(routeId), arrivalStation.getId());
        MappingInfoDTO dispatchMapping = routeMappingService.getMappingInfo(Integer.parseInt(routeId), dispatchStation.getId());
        String travelTime = request.getParameter(AppContextConstant.TRAVEL_TIME);
        Double price = (Double) session.getAttribute(PRICE);

        BookingDTO bookingDTO = BookingDTO.builder()
                .withBookingDate(LocalDateTime.now())
                .withDispatchDate(dispatchMapping.getStationDispatchData())
                .withArrivalDate(arrivalMapping.getStationArrivalDate())
                .withTravelTime(travelTime)
                .withPrice(price)
                .withBookingStatus(OrderStatus.PROCESSING)
                .withUserId(user.getUserId())
                .withRouteId(Integer.parseInt(routeId))
                .withTrainId(Integer.parseInt(trainId))
                .withDispatchStationId(Integer.parseInt(stationIdD))
                .withArrivalStationId(Integer.parseInt(stationIdA))
                .withCarriageId(Integer.parseInt(carriageId))
                .build();

        int bookingId = orderService.saveBooking(bookingDTO);
        System.out.println(" ========= |||||| ======== " + Arrays.toString(seatIds));
        for (String seatId : seatIds) {
            orderService.saveBookingSeat(bookingId, seatId);
        }

        LOGGER.info("done");

        return Router.redirect(Path.COMMAND_ORDERS);
    }
}
