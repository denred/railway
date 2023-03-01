package com.epam.redkin.railway.web.controller.command.common;

import com.epam.redkin.railway.model.dto.CarriageDTO;
import com.epam.redkin.railway.model.entity.Seat;
import com.epam.redkin.railway.model.service.CarriageService;
import com.epam.redkin.railway.model.service.SeatService;
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

import java.util.List;

import static com.epam.redkin.railway.util.constants.AppContextConstant.*;

public class SelectSeatsCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(SelectSeatsCommand.class);

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("started");

        CarriageService carriageService = AppContext.getInstance().getCarriageService();
        SeatService seatService = AppContext.getInstance().getSeatService();
        HttpSession session = request.getSession();

        String carriageType = (String) session.getAttribute(CARRIAGE_TYPE);
        String departureStationId = (String) session.getAttribute(DEPARTURE_STATION_ID);
        String arrivalStationId = (String) session.getAttribute(ARRIVAL_STATION_ID);
        String routeId = (String) session.getAttribute(ROUTE_ID);
        String trainId = (String) session.getAttribute(TRAIN_ID);

        List<CarriageDTO> carriageList = carriageService.getCarriageDTOsByCarriageType(carriageType);
        String carriageId = request.getParameter(CARRIAGE_ID);
        if (StringUtils.isBlank(carriageId)) {
            carriageId = String.valueOf(carriageList.get(0).getCarId());
        }

        List<Seat> seatList = seatService.getCarriageSeats(Integer.parseInt(carriageId));
        seatList = seatService.fillReservedSeats(seatList, routeId, departureStationId, arrivalStationId, trainId);

        carriageList.forEach(carriage -> carriage.setSeats(
                seatService.fillReservedSeats(seatService.getCarriageSeats(carriage.getCarId()),
                        routeId, departureStationId, arrivalStationId, trainId).size()));


        session.setAttribute(CARRIAGE_ID, carriageId);
        session.setAttribute("notedCarriage", carriageId);
        session.setAttribute(SEAT_LIST, seatList);
        session.setAttribute(CARRIAGE_DTO_LIST, carriageList);

        LOGGER.info("done");
        return Router.redirect(Path.COMMAND_SELECT_SEATS_PAGE);
    }
}
