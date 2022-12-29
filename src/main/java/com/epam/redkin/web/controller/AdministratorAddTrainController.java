package com.epam.redkin.web.controller;


import com.epam.redkin.model.entity.Train;
import com.epam.redkin.service.TrainService;
import com.epam.redkin.util.constants.AppContextConstant;
import com.epam.redkin.validator.TrainValidator;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/administrator_add_train")
public class AdministratorAddTrainController extends HttpServlet {
    //private static final Logger LOGGER = Logger.getLogger(AdministratorAddTrainController.class);

    private TrainService trainService;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        TrainValidator trainValidator = new TrainValidator();
        Train train = new Train();
        train.setNumber(request.getParameter("train_number"));
        trainValidator.isValidTrain(train);
        trainService.addTrain(train);
        response.sendRedirect("administrator_account");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("WEB-INF/jsp/administratorAddTrain.jsp").forward(request, response);
    }

    public void init(ServletConfig config) {
        trainService = (TrainService) config.getServletContext().getAttribute(AppContextConstant.TRAIN_SERVICE);
        //LOGGER.trace("administrator_add_train Servlet init");
    }
}
