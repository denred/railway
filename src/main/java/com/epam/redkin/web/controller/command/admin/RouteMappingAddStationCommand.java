package com.epam.redkin.web.controller.command.admin;

import com.epam.redkin.model.dto.MappingInfoDTO;
import com.epam.redkin.model.entity.RoutePoint;
import com.epam.redkin.model.entity.Station;
import com.epam.redkin.model.exception.IncorrectDataException;
import com.epam.redkin.model.service.RouteMappingService;
import com.epam.redkin.model.service.StationService;
import com.epam.redkin.model.validator.RouteMappingValidator;
import com.epam.redkin.web.controller.command.Command;
import com.epam.redkin.web.listener.AppContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;

import static com.epam.redkin.web.controller.Path.COMMAND_ROUTE_MAPPING;
import static com.epam.redkin.web.controller.Path.PAGE_ADMIN_SET_STATION_IN_ROUTE;

public class RouteMappingAddStationCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(RouteMappingAddStationCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("started");
        String forward = PAGE_ADMIN_SET_STATION_IN_ROUTE;
        RouteMappingService routeMappingService = AppContext.getInstance().getRouteMappingService();
        StationService stationService = AppContext.getInstance().getStationService();
        RouteMappingValidator routeMappingValidator = new RouteMappingValidator();
        RoutePoint routToStationMapping = new RoutePoint();

        String routeId = request.getParameter("routs_id");
        String arrivalDate = request.getParameter("station_arrival_date");
        String dispatchDate = request.getParameter("station_dispatch_data");
        String station = request.getParameter("station_station");
        String stationOrder = request.getParameter("station_order");

        if (StringUtils.isNoneBlank(routeId, arrivalDate, dispatchDate, station, stationOrder)) {
            routToStationMapping.setRouteId(Integer.parseInt(routeId));
            routToStationMapping.setStationId(Integer.parseInt(station));

            List<MappingInfoDTO> mappingList = routeMappingService
                    .getAllRoutToStationMappingListById(Integer.parseInt(routeId));
            try {

                routToStationMapping.setArrival(LocalDateTime.parse(arrivalDate));
                routToStationMapping.setDispatch(LocalDateTime.parse(dispatchDate));

                if (!contains(mappingList, Integer.parseInt(stationOrder))) {
                    routToStationMapping.setOrderId(Integer.parseInt(stationOrder));
                } else {
                    LOGGER.error("Incorrect data entered");
                    throw new IncorrectDataException("Incorrect data entered");
                }
            } catch (NumberFormatException | DateTimeParseException e) {
                LOGGER.error("Incorrect data entered");
                throw new IncorrectDataException("Incorrect data entered", e);
            }
            routeMappingValidator.isValidRoutToStationMapping(routToStationMapping, mappingList);
            routeMappingService.addRoutToStationMapping(routToStationMapping);
            forward = COMMAND_ROUTE_MAPPING;
        } else {
            List<Station> stationList = stationService.getAllStationList();
            request.setAttribute("routs_id", routeId);
            request.setAttribute("station_list", stationList);
        }
        LOGGER.info("done");
        return forward;
    }

    public static boolean contains(final List<MappingInfoDTO> array, final int order) {
        boolean result = false;

        for (MappingInfoDTO mappingInfoDto : array) {
            if (mappingInfoDto.getOrder() == order) {
                result = true;
                break;
            }
        }
        return result;
    }
}
