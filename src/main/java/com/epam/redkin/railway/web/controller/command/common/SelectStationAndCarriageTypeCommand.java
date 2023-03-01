package com.epam.redkin.railway.web.controller.command.common;

import com.epam.redkin.railway.model.dto.MappingInfoDTO;
import com.epam.redkin.railway.model.entity.CarriageType;
import com.epam.redkin.railway.model.service.CarriageService;
import com.epam.redkin.railway.model.service.RouteMappingService;
import com.epam.redkin.railway.web.controller.Path;
import com.epam.redkin.railway.web.controller.command.Command;
import com.epam.redkin.railway.web.controller.command.Router;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Set;

import static com.epam.redkin.railway.util.constants.AppContextConstant.*;

public class SelectStationAndCarriageTypeCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(SelectStationAndCarriageTypeCommand.class);
    private final CarriageService carriageService;
    private final RouteMappingService routeMappingService;

    public SelectStationAndCarriageTypeCommand(CarriageService carriageService, RouteMappingService routeMappingService) {
        this.carriageService = carriageService;
        this.routeMappingService = routeMappingService;
    }

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("started");

        HttpSession session = request.getSession();

        String trainId = request.getParameter(TRAIN_ID);
        String routeId = request.getParameter(ROUTE_ID);
        String carriageType = request.getParameter(CARRIAGE_TYPE);
        String departure_station_id = request.getParameter(DEPARTURE_STATION_ID);
        String arrival_station_id = request.getParameter(ARRIVAL_STATION_ID);

        if (StringUtils.isNoneBlank(trainId, routeId, carriageType, departure_station_id, arrival_station_id)) {
            List<MappingInfoDTO> routeMappingInfoDTOs = routeMappingService.getRouteMappingInfoDTOs(Integer.parseInt(routeId));
            Set<CarriageType> carriageTypes = carriageService.getCarriageTypesByTrainId(Integer.parseInt(trainId));

            session.setAttribute(STATION_LIST, routeMappingInfoDTOs);
            session.setAttribute(CARRIAGE_TYPE_LIST, carriageTypes);
            session.setAttribute(ROUTE_ID, routeId);
            session.setAttribute(TRAIN_ID, trainId);
            session.setAttribute(CARRIAGE_TYPE, carriageType.toUpperCase());
            session.setAttribute(DEPARTURE_STATION_ID, departure_station_id);
            session.setAttribute(ARRIVAL_STATION_ID, arrival_station_id);
        }

        return Router.redirect(Path.COMMAND_SELECT_SEATS_NUMBER);
    }
}
