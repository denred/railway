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

@WebServlet("/administrator_edit_info_train")
public class AdministratorEditInfoTrainController extends HttpServlet {
    //private static final Logger LOGGER = Logger.getLogger(AdministratorEditInfoTrainController.class);

    private TrainService trainService;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        TrainValidator trainValidator = new TrainValidator();
        Train train = new Train();
        train.setId(Integer.parseInt(request.getParameter("train_id")));
        train.setNumber(request.getParameter("train_number"));
        trainValidator.isValidTrain(train);
        trainService.updateTrain(train);
        response.sendRedirect("administrator_account");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String trainId = request.getParameter("train_id");
        Train train = trainService.getTrainById(Integer.parseInt(trainId));
        request.setAttribute("current_train", train);
        request.getRequestDispatcher("WEB-INF/jsp/administratorEditInfoTrain.jsp").forward(request, response);
    }

    @Override
    public void init(ServletConfig config) {
        trainService = (TrainService) config.getServletContext().getAttribute(AppContextConstant.TRAIN_SERVICE);
       // LOGGER.trace("administrator_edit_info_train Servlet init");

    }
}


