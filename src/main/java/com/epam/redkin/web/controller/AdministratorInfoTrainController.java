package com.epam.redkin.web.controller;


import com.epam.redkin.model.entity.*;
import com.epam.redkin.model.service.TrainService;
import com.epam.redkin.model.service.*;
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

@WebServlet("/administrator_info_train")
public class AdministratorInfoTrainController extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(AdministratorInfoTrainController.class);
    private TrainService trainService;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int page = 1;
        int recordsPerPage = 5;
        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }
        int noOfRecords = trainService.getTrainListSize();
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
        List<Train> trainList = trainService.getTrainListBySetRecords(
                (page - 1) * recordsPerPage,
                recordsPerPage * page);
        request.setAttribute("recordsPerPage", recordsPerPage);
        request.setAttribute("noOfPages", noOfPages);
        request.setAttribute("currentPage", page);
        request.setAttribute("train_list", trainList);
        request.getRequestDispatcher("WEB-INF/jsp/administratorInfoTrain.jsp").forward(request, response);
    }

    @Override
    public void init(ServletConfig config) {
        trainService = (TrainService) config.getServletContext().getAttribute((AppContextConstant.TRAIN_SERVICE));
        LOGGER.trace("administrator_info_train Servlet init");
    }
}
