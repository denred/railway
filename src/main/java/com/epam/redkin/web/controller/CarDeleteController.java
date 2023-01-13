package com.epam.redkin.web.controller;


import com.epam.redkin.model.service.CarriageService;
import com.epam.redkin.util.constants.AppContextConstant;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/car_delete")
public class CarDeleteController extends HttpServlet {
    //private static final Logger LOGGER = Logger.getLogger(CarDeleteController.class);

    private CarriageService carriageService;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String carId = request.getParameter("car_id");
        carriageService.removeCar(Integer.parseInt(carId));
        response.sendRedirect("administrator_account");
    }

    @Override
    public void init(ServletConfig config) {
        carriageService = (CarriageService) config.getServletContext().getAttribute(AppContextConstant.CARS_SERVICE);
        //LOGGER.trace("car_delete Servlet init");

    }
}


