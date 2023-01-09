package com.epam.redkin.web.controller;


import com.epam.redkin.model.dto.CarriageDTO;
import com.epam.redkin.model.entity.Order;
import com.epam.redkin.service.*;
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

@WebServlet("/administrator_info_carriage")
public class AdministratorInfoCarriageController extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(AdministratorInfoCarriageController.class);
    private CarriageService carriageService;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int page = 1;
        int recordsPerPage = 5;
        if (request.getParameter("page") != null)
            page = Integer.parseInt(request.getParameter("page"));
        List<CarriageDTO> carriageDtoList = carriageService.getCarriageDtoListByCurrentRecordAndRecordsPerPage(
                (page - 1) * recordsPerPage,
                recordsPerPage * page);
        int noOfRecords = carriageService.getRouteListSize();
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
        request.setAttribute("recordsPerPage", recordsPerPage);
        request.setAttribute("noOfPages", noOfPages);
        request.setAttribute("currentPage", page);
        request.setAttribute("car_list", carriageDtoList);
        request.getRequestDispatcher("WEB-INF/jsp/administratorInfoCarriage.jsp").forward(request, response);
    }

    @Override
    public void init(ServletConfig config) {
        carriageService = (CarriageService) config.getServletContext().getAttribute((AppContextConstant.CARS_SERVICE));
        LOGGER.trace("administrator_info_carriage Servlet init");
    }
}
