package com.epam.redkin.web.controller;


import com.epam.redkin.model.dto.MappingInfoDTO;
import com.epam.redkin.service.RouteMappingService;
import com.epam.redkin.util.constants.AppContextConstant;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

@WebServlet("/administrator_details_set_rout")
public class AdministratorDetailsSetRoutController extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(AdministratorDetailsSetRoutController.class);
    private RouteMappingService routeMappingService;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String routeId = request.getParameter("routs_id");
        String stationId = request.getParameter("station_id");
        request.setAttribute("station_id", stationId);
        request.setAttribute("routs_id", routeId);
        List<MappingInfoDTO> AllRoutToStationMappingListById = routeMappingService.getAllRoutToStationMappingListById(Integer.parseInt(routeId));
        LOGGER.debug(AllRoutToStationMappingListById.toString());
        request.setAttribute("rout_m_list", AllRoutToStationMappingListById);
        request.getRequestDispatcher("WEB-INF/jsp/administratorDetailsSetRout.jsp").forward(request, response);
    }

    @Override
    public void init(ServletConfig config) {
        routeMappingService = (RouteMappingService) config.getServletContext().getAttribute((AppContextConstant.ROUT_TO_STATION_MAPPING_SERVICE));
        LOGGER.trace("administrator_details_set_rout Servlet init");
    }
}
