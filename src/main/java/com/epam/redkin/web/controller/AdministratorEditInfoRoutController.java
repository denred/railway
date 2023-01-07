package com.epam.redkin.web.controller;


import com.epam.redkin.model.dto.RouteInfoDTO;
import com.epam.redkin.model.entity.Route;
import com.epam.redkin.model.entity.Train;
import com.epam.redkin.service.RouteService;
import com.epam.redkin.service.TrainService;
import com.epam.redkin.util.constants.AppContextConstant;
import com.epam.redkin.validator.RoutValidator;
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

@WebServlet("/administrator_edit_info_rout")
public class AdministratorEditInfoRoutController extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(AdministratorEditInfoRoutController.class);

    private RouteService routeService;
    private TrainService trainService;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        RoutValidator routValidator = new RoutValidator();
        Route route = new Route();
        route.setRouteId(Integer.parseInt(request.getParameter("routs_id")));
        route.setRouteName(request.getParameter("rout_name"));
        route.setRouteNumber(Integer.parseInt(request.getParameter("rout_number")));
        route.setTrainId(Integer.parseInt(request.getParameter("train_number")));
        routValidator.isValidRout(route);
        routeService.updateRout(route);
        response.sendRedirect("administrator_account");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String routsId = request.getParameter("routs_id");
        RouteInfoDTO rout = routeService.getRoutById(Integer.parseInt(routsId));
        request.setAttribute("current_rout", rout);
        List<Train> trainList = trainService.getAllTrainList();
        request.setAttribute("trainList", trainList);
        request.getRequestDispatcher("WEB-INF/jsp/administratorEditInfoRout.jsp").forward(request, response);
    }

    @Override
    public void init(ServletConfig config) {
        routeService = (RouteService) config.getServletContext().getAttribute(AppContextConstant.ROUT_SERVICE);
        trainService = (TrainService) config.getServletContext().getAttribute(AppContextConstant.TRAIN_SERVICE);
        LOGGER.trace("administrator_edit_info_rout Servlet init");

    }
}
