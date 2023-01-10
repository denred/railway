package com.epam.redkin.web.controller;


import com.epam.redkin.service.OrderService;
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

@WebServlet("/cancel_order")
public class CancelOrderController extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(CancelOrderController.class);
    private OrderService orderService;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String orderId = req.getParameter("order_id");
        String userId = req.getParameter("user_id");
        orderService.cancelOrder(Integer.parseInt(orderId));
        resp.sendRedirect("user_account?user_id=" + userId);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {


    }

    @Override
    public void init(ServletConfig config) {
        orderService = (OrderService) config.getServletContext().getAttribute(AppContextConstant.ORDER_SERVICE);
        LOGGER.trace("cancel_order Servlet init");

    }
}
