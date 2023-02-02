package com.epam.redkin.railway.web.controller.command.common;

import com.epam.redkin.railway.model.dto.RouteInfoDTO;
import com.epam.redkin.railway.model.entity.Carriage;
import com.epam.redkin.railway.model.exception.IncorrectDataException;
import com.epam.redkin.railway.model.service.CarriageService;
import com.epam.redkin.railway.model.service.RouteService;
import com.epam.redkin.railway.web.controller.Path;
import com.epam.redkin.railway.web.controller.command.Command;
import com.epam.redkin.railway.appcontext.AppContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

import static com.epam.redkin.railway.util.constants.AppContextConstant.CARRIAGE_TYPE;
import static com.epam.redkin.railway.util.constants.AppContextConstant.TRAIN_ID;

public class SelectCarriageAndSeatsCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(SelectCarriageAndSeatsCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("started");
        RouteService routeService = AppContext.getInstance().getRouteService();
        CarriageService carriageService = AppContext.getInstance().getCarriageService();
        String departureStation = request.getParameter("departure_station");
        String departureStationId = request.getParameter("departure_station_id");
        String arrivalStation = request.getParameter("arrival_station");
        String arrivalStationId = request.getParameter("arrival_station_id");
        String carriageType = request.getParameter(CARRIAGE_TYPE);
        LOGGER.debug("carriageType= " + carriageType);
        String trainId = request.getParameter(TRAIN_ID);
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
        String routsId = request.getParameter("routs_id");
        RouteInfoDTO routeInfoDto = routeService.getRouteInfoById(Integer.parseInt(routsId));
        List<Carriage> carList = carriageService
                .getCarByTrainIdAndCarType(routeInfoDto.getTrainId(), carriageType)
                .stream()
                .distinct()
                .collect(Collectors.toList());
        request.setAttribute("station1", station1);
        request.setAttribute("station2", station2);
        request.setAttribute("travel_time", travelTime);
        //request.setAttribute("departure_station", departureStation);
        request.setAttribute("departure_station", departureStationId);
        request.setAttribute("departure_station_id", departureStationId);
        request.setAttribute("arrival_station", arrivalStationId);
        //request.setAttribute("arrival_station", arrivalStation);
        request.setAttribute("arrival_station_id", arrivalStationId);
        request.setAttribute("departure_date", departureDate);
        request.setAttribute("routs_id", routsId);
        request.setAttribute(CARRIAGE_TYPE, carriageType);
        request.setAttribute(TRAIN_ID, trainId);
        request.setAttribute("car_list", carList);
        return Path.PAGE_SELECT_CARRIAGE_AND_COUNT_SEATS;
    }
}
