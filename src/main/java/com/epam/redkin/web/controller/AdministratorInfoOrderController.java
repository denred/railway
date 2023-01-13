package com.epam.redkin.web.controller;


import com.epam.redkin.model.entity.*;
import com.epam.redkin.model.service.OrderService;
import com.epam.redkin.model.service.RouteService;
import com.epam.redkin.util.constants.AppContextConstant;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

@WebServlet("/administrator_info_order")
public class AdministratorInfoOrderController extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(AdministratorInfoOrderController.class);
    private OrderService orderService;
    private RouteService routeService;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int page = 1;
        int recordsPerPage = 2;
        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }
        int noOfRecords = orderService.getOrderListSize();
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
        List<Order> orderList = orderService.getOrderListByCurrentRecordAndRecordsPerPage(
                (page - 1) * recordsPerPage,
                recordsPerPage * page);
        for (Order order : orderList) {
            order.setRouteName(routeService.getRoutById(order.getRouteId()).getRoutName());
        }
        request.setAttribute("recordsPerPage", recordsPerPage);
        request.setAttribute("noOfPages", noOfPages);
        request.setAttribute("currentPage", page);
        request.setAttribute("order_list", orderList);
        HttpSession session = request.getSession();
        request.setAttribute("language", session.getAttribute(AppContextConstant.LOCALE));
        request.getRequestDispatcher("WEB-INF/jsp/administratorInfoOrder.jsp").forward(request, response);
    }

    @Override
    public void init(ServletConfig config) {
        orderService = (OrderService) config.getServletContext().getAttribute(AppContextConstant.ORDER_SERVICE);
        routeService = (RouteService) config.getServletContext().getAttribute((AppContextConstant.ROUT_SERVICE));
        LOGGER.trace("administrator_info_order Servlet init");
    }
}
