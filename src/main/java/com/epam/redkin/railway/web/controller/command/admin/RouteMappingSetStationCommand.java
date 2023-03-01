package com.epam.redkin.railway.web.controller.command.admin;

import com.epam.redkin.railway.model.dto.MappingInfoDTO;
import com.epam.redkin.railway.model.entity.RoutePoint;
import com.epam.redkin.railway.model.entity.Station;
import com.epam.redkin.railway.model.exception.IncorrectDataException;
import com.epam.redkin.railway.model.service.RouteMappingService;
import com.epam.redkin.railway.model.service.StationService;
import com.epam.redkin.railway.model.validator.RouteMappingValidator;
import com.epam.redkin.railway.web.controller.Path;
import com.epam.redkin.railway.web.controller.command.Command;
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

import static com.epam.redkin.railway.model.repository.impl.Constants.*;
import static com.epam.redkin.railway.util.constants.AppContextConstant.*;
import static com.epam.redkin.railway.util.constants.AppContextConstant.ROUTE_ID;
import static com.epam.redkin.railway.util.constants.AppContextConstant.STATION_ID;

public class RouteMappingSetStationCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(RouteMappingSetStationCommand.class);
    private final RouteMappingService routeMappingService;
    private final StationService stationService;
    private final RouteMappingValidator routeMappingValidator;

    public RouteMappingSetStationCommand(RouteMappingService routeMappingService, StationService stationService, RouteMappingValidator routeMappingValidator) {
        this.routeMappingService = routeMappingService;
        this.stationService = stationService;
        this.routeMappingValidator = routeMappingValidator;
    }

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("started");

        HttpSession session = request.getSession();

        String routeId = request.getParameter(ROUTE_ID);
        String stationId = request.getParameter(STATION_ID);
        String arrivalDate = request.getParameter(STATION_ARRIVAL_DATE);
        String dispatchDate = request.getParameter(STATION_DISPATCH_DATE);
        String station = request.getParameter(STATION_NAME);
        String stationOrder = request.getParameter(STATION_ORDER);
        String operationStatus = request.getParameter(OPERATION_STATUS);
        String prevStationId = request.getParameter(STATION_ID_BEFORE_UPDATE);

        if (StringUtils.isNoneBlank(routeId, arrivalDate, dispatchDate, station, stationOrder)) {
            RoutePoint routeToStationMapping = RoutePoint.builder()
                    .routeId(Integer.parseInt(routeId))
                    .stationId(Integer.parseInt(station))
                    .orderId(Integer.parseInt(stationOrder))
                    .build();

            List<MappingInfoDTO> mappingList = routeMappingService
                    .getRouteMappingInfoDTOs(Integer.parseInt(routeId));
            try {
                routeToStationMapping.setArrival(LocalDateTime.parse(arrivalDate));
                routeToStationMapping.setDispatch(LocalDateTime.parse(dispatchDate));
            } catch (NumberFormatException | DateTimeParseException e) {
                LOGGER.error("Incorrect data entered");
                throw new IncorrectDataException("Incorrect data entered", e);
            }
            if (operationStatus.equals(OPERATION_STATUS_ADD)) {
                routeMappingValidator.isValidRoutToStationMapping(routeToStationMapping, mappingList);
                routeMappingService.addRoutToStationMapping(routeToStationMapping);
            } else {
                routeMappingValidator.isValidUpdateRoutToStationMapping(routeToStationMapping);
                routeMappingService.updateRoutToStationMapping(routeToStationMapping, Integer.parseInt(prevStationId));
            }
            return Router.redirect(Path.COMMAND_ROUTE_MAPPING);
        } else if (StringUtils.isNoneBlank(stationId, routeId)) {
            List<Station> stationList = stationService.getStations();
            MappingInfoDTO mappingInfo = routeMappingService
                    .getMappingInfo(Integer.parseInt(routeId), Integer.parseInt(stationId));

            session.setAttribute(ROUTE_ID, routeId);
            request.setAttribute(STATION_ID, stationId);
            request.setAttribute(CURRENT_ROUTE, mappingInfo);
            request.setAttribute(STATION_LIST, stationList);
        } else {
            List<Station> stationList = stationService.getStations();
            List<MappingInfoDTO> mappingList = routeMappingService
                    .getRouteMappingInfoDTOs(Integer.parseInt(routeId));
            if (!mappingList.isEmpty()) {
                LocalDateTime dispatchDateTime = mappingList.get(mappingList.size() - 1).getStationDispatchData();
                LocalDateTime arrivalDateTime = mappingList.get(mappingList.size() - 1).getStationArrivalDate();
                mappingList.get(mappingList.size() - 1).setStationArrivalDate(dispatchDateTime.plusHours(1));
                mappingList.get(mappingList.size() - 1).setStationDispatchData(arrivalDateTime.plusMinutes(70));
                mappingList.get(mappingList.size() - 1).setOrder(mappingList.get(mappingList.size() - 1).getOrder() + 1);
                request.setAttribute(CURRENT_ROUTE, mappingList.get(mappingList.size() - 1));
                request.setAttribute(STATION_ID, mappingList.get(mappingList.size() - 1).getStationId());
            }
            session.setAttribute(ROUTE_ID, routeId);
            request.setAttribute(STATION_LIST, stationList);
        }

        request.setAttribute(OPERATION_STATUS, operationStatus);
        LOGGER.info("done");
        return Router.forward(Path.PAGE_ADMIN_SET_STATION_IN_ROUTE);
    }
}
