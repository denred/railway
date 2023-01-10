package com.epam.redkin.web.controller;


import com.epam.redkin.model.dto.MappingInfoDTO;
import com.epam.redkin.model.entity.RoutePoint;
import com.epam.redkin.model.entity.Station;
import com.epam.redkin.model.exception.IncorrectDataException;
import com.epam.redkin.service.RouteMappingService;
import com.epam.redkin.service.StationService;
import com.epam.redkin.util.constants.AppContextConstant;
import com.epam.redkin.validator.RouteMappingValidator;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;

@WebServlet("/administrator_set_rout_mapping")
public class AdministratorSetRoutController extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(AdministratorSetRoutController.class);
    private StationService stationService;
    private RouteMappingService routeMappingService;

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

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        RouteMappingValidator routeMappingValidator = new RouteMappingValidator();
        RoutePoint routToStationMapping = new RoutePoint();
        String routsId = request.getParameter("routs_id");
        routToStationMapping.setRouteId(Integer.parseInt(routsId));
        routToStationMapping.setStationId(Integer.parseInt(request.getParameter("station_station")));
        List<MappingInfoDTO> mappingList = routeMappingService.getAllRoutToStationMappingListById(Integer.parseInt(routsId));
        try {
            int stationOrder = Integer.parseInt(request.getParameter("station_order"));
            routToStationMapping.setArrival(LocalDateTime.parse(request.getParameter("station_arrival_date")));
            routToStationMapping.setDispatch(LocalDateTime.parse(request.getParameter("station_dispatch_data")));
            if (!contains(mappingList, stationOrder)) {
                routToStationMapping.setOrderId(stationOrder);
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

        response.sendRedirect("administrator_details_set_rout?routs_id=" + routsId);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String routsId = request.getParameter("routs_id");
        request.setAttribute("routs_id", routsId);
        List<Station> stationList = stationService.getAllStationList();
        request.setAttribute("station_list", stationList);


        request.getRequestDispatcher("WEB-INF/jsp/administratorSetRout.jsp").forward(request, response);
    }

    @Override
    public void init(ServletConfig config) {
        stationService = (StationService) config.getServletContext().getAttribute((AppContextConstant.STATION_SERVICE));
        routeMappingService = (RouteMappingService) config.getServletContext().getAttribute((AppContextConstant.ROUT_TO_STATION_MAPPING_SERVICE));
        LOGGER.trace("administrator_set_rout_mapping Servlet init");
    }
}
