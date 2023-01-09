package com.epam.redkin.web.controller;


import com.epam.redkin.service.RouteService;
import com.epam.redkin.util.constants.AppContextConstant;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@WebServlet("/rout_delete")
public class RoutDeleteController extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(RoutDeleteController.class);

    private RouteService routeService;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String routsId = request.getParameter("routs_id");
        routeService.removeRout(Integer.parseInt(routsId));
        response.sendRedirect("administrator_info_route");
    }

    @Override
    public void init(ServletConfig config) {
        routeService = (RouteService) config.getServletContext().getAttribute(AppContextConstant.ROUT_SERVICE);
        LOGGER.trace("rout_delete Servlet init");
    }
}
