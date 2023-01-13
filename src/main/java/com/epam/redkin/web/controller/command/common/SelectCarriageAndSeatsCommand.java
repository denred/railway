package com.epam.redkin.web.controller.command.common;

import com.epam.redkin.model.dto.RouteInfoDTO;
import com.epam.redkin.model.entity.Carriage;
import com.epam.redkin.model.exception.IncorrectDataException;
import com.epam.redkin.model.service.CarriageService;
import com.epam.redkin.model.service.RouteService;
import com.epam.redkin.web.controller.command.Command;
import com.epam.redkin.web.listener.AppContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

import static com.epam.redkin.web.controller.Path.PAGE_SELECT_CARRIAGE_AND_COUNT_SEATS;

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
        String carType = request.getParameter("car_type");
        String trainId = request.getParameter("train_id");
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
        String routsId = request.getParameter("routs_id");
        RouteInfoDTO routeInfoDto = routeService.getRoutById(Integer.parseInt(routsId));
        List<Carriage> carList = carriageService
                .getCarByTrainIdAndCarType(routeInfoDto.getTrainId(), carType)
                .stream()
                .distinct()
                .collect(Collectors.toList());
        request.setAttribute("station1", station1);
        request.setAttribute("station2", station2);
        request.setAttribute("travel_time", travelTime);
        request.setAttribute("departure_station", departureStation);
        request.setAttribute("departure_station_id", departureStationId);
        request.setAttribute("arrival_station", arrivalStation);
        request.setAttribute("arrival_station_id", arrivalStationId);
        request.setAttribute("departure_date", departureDate);
        request.setAttribute("routs_id", routsId);
        request.setAttribute("car_type", carType);
        request.setAttribute("user_id", userId);
        request.setAttribute("train_id", trainId);
        request.setAttribute("car_list", carList);
        return PAGE_SELECT_CARRIAGE_AND_COUNT_SEATS;
    }
}
