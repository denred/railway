package com.epam.redkin.web.controller;


import com.epam.redkin.model.entity.Route;
import com.epam.redkin.model.entity.Train;
import com.epam.redkin.service.RoutService;
import com.epam.redkin.service.TrainService;
import com.epam.redkin.util.constants.AppContextConstant;
import com.epam.redkin.validator.RoutValidator;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/administrator_add_rout")
public class AdministratorAddRoutController extends HttpServlet {

    //private static final Logger LOGGER = Logger.getLogger(AdministratorAddRoutController.class);
    private RoutService routService;
    private TrainService trainService;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        RoutValidator routValidator = new RoutValidator();
        Route rout = new Route();
        rout.setRouteName(request.getParameter("rout_name"));
        rout.setRouteNumber(Integer.parseInt(request.getParameter("rout_number")));
        rout.setTrainId(Integer.parseInt(request.getParameter("train_number")));
        routValidator.isValidRout(rout);
        routService.addRout(rout);
        response.sendRedirect("administrator_account");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Train> trainList = trainService.getAllTrainList();
        request.setAttribute("trainList", trainList);
        request.getRequestDispatcher("WEB-INF/jsp/administratorAddRout.jsp").forward(request, response);
    }

    public void init(ServletConfig config) {
        routService = (RoutService) config.getServletContext().getAttribute(AppContextConstant.ROUT_SERVICE);
        trainService = (TrainService) config.getServletContext().getAttribute(AppContextConstant.TRAIN_SERVICE);
       // LOGGER.trace("administrator_add_rout Servlet init");

    }
}
