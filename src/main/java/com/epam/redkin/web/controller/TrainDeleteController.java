package com.epam.redkin.web.controller;


import com.epam.redkin.model.service.TrainService;
import com.epam.redkin.util.constants.AppContextConstant;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@WebServlet("/train_delete")
public class TrainDeleteController extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(TrainDeleteController.class);

    private TrainService trainService;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String trainId = request.getParameter("train_id");
        trainService.removeTrain(Integer.parseInt(trainId));
        response.sendRedirect("administrator_info_train");
    }

    @Override
    public void init(ServletConfig config) {
        trainService = (TrainService) config.getServletContext().getAttribute(AppContextConstant.TRAIN_SERVICE);
        LOGGER.trace("train_delete Servlet init");
    }
}