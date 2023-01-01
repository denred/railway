package com.epam.redkin.web.controller;


import com.epam.redkin.model.dto.MappingInfoDTO;
import com.epam.redkin.model.entity.RoutePoint;
import com.epam.redkin.model.entity.Station;
import com.epam.redkin.model.exception.IncorrectDataException;
import com.epam.redkin.service.RoutMappingService;
import com.epam.redkin.service.StationService;
import com.epam.redkin.util.constants.AppContextConstant;
import com.epam.redkin.validator.RoutMappingValidator;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;

@WebServlet("/administrator_edit_info_details_set_rout")
public class AdministratorEditInfoDetailsSetRoutController extends HttpServlet {
    //private static final Logger LOGGER = Logger.getLogger(AdministratorEditInfoDetailsSetRoutController.class);
    private RoutMappingService routMappingService;
    private StationService stationService;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        RoutMappingValidator routMappingValidator = new RoutMappingValidator();
        RoutePoint routToStationMapping = new RoutePoint();
        String routsId = request.getParameter("routs_id");
        String stationId = request.getParameter("station_current_id");
        routToStationMapping.setRouteId(Integer.parseInt(routsId));
        routToStationMapping.setStationId(Integer.parseInt(stationId));
        routToStationMapping.setStationId(Integer.parseInt(request.getParameter("station_station")));
        try {
            routToStationMapping.setArrival(LocalDateTime.parse(request.getParameter("station_arrival_date")));
            routToStationMapping.setDispatch(LocalDateTime.parse(request.getParameter("station_dispatch_data")));
            routToStationMapping.setOrderId(Integer.parseInt(request.getParameter("station_order")));
        } catch (NumberFormatException | DateTimeParseException e) {
            //LOGGER.error("Incorrect data entered");
            throw new IncorrectDataException("Incorrect data entered", e);
        }
        routMappingValidator.isValidUpdateRoutToStationMapping(routToStationMapping);
        routMappingService.updateRoutToStationMapping(routToStationMapping, Integer.parseInt(stationId));
        response.sendRedirect("administrator_details_set_rout?routs_id=" + routsId);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String routsId = request.getParameter("routs_id");
        String stationId = request.getParameter("station_id");
        List<Station> stationList = stationService.getAllStationList();
        MappingInfoDTO MappingInfo = routMappingService.getMappingInfo(Integer.parseInt(routsId), Integer.parseInt(stationId));
        request.setAttribute("routs_id", routsId);
        request.setAttribute("station_id", stationId);
        request.setAttribute("current_rout", MappingInfo);
        request.setAttribute("station_list", stationList);

        request.getRequestDispatcher("WEB-INF/jsp/administratorEditInfoDetailsSetRout.jsp").forward(request, response);
    }

    @Override
    public void init(ServletConfig config) {
        routMappingService = (RoutMappingService) config.getServletContext().getAttribute(AppContextConstant.ROUT_TO_STATION_MAPPING_SERVICE);
        stationService = (StationService) config.getServletContext().getAttribute((AppContextConstant.STATION_SERVICE));
        //LOGGER.trace("administrator_edit_info_details_set_rout Servlet init");

    }
}
