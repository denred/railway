package com.epam.redkin.railway.web.controller.command.common;

import com.epam.redkin.railway.model.dto.RouteInfoDTO;
import com.epam.redkin.railway.model.entity.Carriage;
import com.epam.redkin.railway.model.entity.Seat;
import com.epam.redkin.railway.model.service.CarriageService;
import com.epam.redkin.railway.model.service.RouteService;
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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.epam.redkin.railway.util.constants.AppContextConstant.*;

public class SelectSeatsCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(SelectSeatsCommand.class);

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("started");
        Router router = new Router();
        router.setRouteType(Router.RouteType.REDIRECT);
        router.setPagePath(Path.COMMAND_SELECT_SEATS_PAGE);

        RouteService routeService = AppContext.getInstance().getRouteService();
        CarriageService carriageService = AppContext.getInstance().getCarriageService();
        SeatService seatService = AppContext.getInstance().getSeatService();
        HttpSession session = request.getSession();

        String carriageType = (String) session.getAttribute(CARRIAGE_TYPE);
        String departure_station_id = (String) session.getAttribute(DEPARTURE_STATION_ID);
        String arrival_station_id = (String) session.getAttribute(ARRIVAL_STATION_ID);
        String routeId = (String) session.getAttribute(ROUTE_ID);
        List<Carriage> carriageList = new ArrayList<>();

        if (StringUtils.isNoneBlank(carriageType, routeId, departure_station_id, arrival_station_id)) {
            RouteInfoDTO routeInfoDto = routeService.getRouteInfoById(Integer.parseInt(routeId));
            carriageList = carriageService
                    .getCarByTrainIdAndCarType(routeInfoDto.getTrainId(), carriageType)
                    .stream()
                    .distinct()
                    .collect(Collectors.toList());
        }


        String carriageId = request.getParameter(CARRIAGE_ID);
        if(StringUtils.isBlank(carriageId)){
            carriageId = String.valueOf(carriageList.get(0).getCarriageId());
        }
            List<Seat> seatList = seatService.getSeatByCarId(Integer.parseInt(carriageId));
            session.setAttribute(CARRIAGE_ID, carriageId);
            session.setAttribute("notedCarriage", carriageId);
            session.setAttribute(SEAT_LIST, seatList);
            session.setAttribute(CARRIAGE_DTO_LIST, carriageList);

        LOGGER.info("done");
        return router;
    }
}
