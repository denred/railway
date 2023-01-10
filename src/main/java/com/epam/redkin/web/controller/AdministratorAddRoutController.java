package com.epam.redkin.web.controller;


import com.epam.redkin.model.entity.Route;
import com.epam.redkin.model.entity.Train;
import com.epam.redkin.service.RouteService;
import com.epam.redkin.service.TrainService;
import com.epam.redkin.util.constants.AppContextConstant;
import com.epam.redkin.validator.RouteValidator;
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

@WebServlet("/administrator_add_rout")
public class AdministratorAddRoutController extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(AdministratorAddRoutController.class);
    private RouteService routeService;
    private TrainService trainService;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        RouteValidator routeValidator = new RouteValidator();
        Route rout = new Route();
        rout.setRouteName(request.getParameter("rout_name"));
        rout.setRouteNumber(Integer.parseInt(request.getParameter("rout_number")));
        rout.setTrainId(Integer.parseInt(request.getParameter("train_number")));
        routeValidator.isValidRout(rout);
        routeService.addRout(rout);
        response.sendRedirect("administrator_info_route");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Train> trainList = trainService.getAllTrainList();
        request.setAttribute("trainList", trainList);
        request.getRequestDispatcher("WEB-INF/jsp/administratorAddRout.jsp").forward(request, response);
    }

    public void init(ServletConfig config) {
        routeService = (RouteService) config.getServletContext().getAttribute(AppContextConstant.ROUT_SERVICE);
        trainService = (TrainService) config.getServletContext().getAttribute(AppContextConstant.TRAIN_SERVICE);
        LOGGER.trace("administrator_add_rout Servlet init");

    }
}
