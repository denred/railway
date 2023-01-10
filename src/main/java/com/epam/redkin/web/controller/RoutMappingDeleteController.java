package com.epam.redkin.web.controller;


import com.epam.redkin.service.RouteMappingService;
import com.epam.redkin.util.constants.AppContextConstant;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/remove_rout_mapping")
public class RoutMappingDeleteController extends HttpServlet {
    //private static final Logger LOGGER = Logger.getLogger(RoutMappingDeleteController.class);

    private RouteMappingService routeMappingService;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String routsId = request.getParameter("routs_id");
        String stationId = request.getParameter("station_id");
        routeMappingService.removeRoutToStationMapping(Integer.parseInt(routsId), Integer.parseInt(stationId));

        response.sendRedirect("administrator_details_set_rout?routs_id=" + routsId);
    }

    @Override
    public void init(ServletConfig config) {
        routeMappingService = (RouteMappingService) config.getServletContext().getAttribute(AppContextConstant.ROUT_TO_STATION_MAPPING_SERVICE);
        //LOGGER.trace("remove_rout_mapping Servlet init");
    }
}
