package com.epam.redkin.web.controller;


import com.epam.redkin.model.dto.RouteInfoDTO;
import com.epam.redkin.model.entity.Order;
import com.epam.redkin.service.OrderService;
import com.epam.redkin.service.RoutService;
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

//http://localhost:8087/user_account?user_id=1&account=User+account
@WebServlet("/user_account")
public class UserAccountController extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserAccountController.class);
    private OrderService orderService;
    private RoutService routService;


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userId = request.getParameter("user_id");
        request.setAttribute("user_id", userId);
        List<Order> orderList = orderService.getOrderByUserId(Integer.parseInt(userId));

        for (int i = 0; i <= orderList.size() - 1; i++) {
            RouteInfoDTO routeInfoDto = routService.getRoutById(orderList.get(i).getRouteId());
            orderList.get(i).setRouteId(routeInfoDto.getRoutsId());
        }
        request.setAttribute("order_list", orderList);
        Double priceOfSuccessfulOrders = orderService.getPriceOfSuccessfulOrders(Integer.parseInt(userId));
        request.setAttribute("sum", priceOfSuccessfulOrders);


        request.getRequestDispatcher("WEB-INF/jsp/userAccount.jsp").forward(request, response);
    }

    @Override
    public void init(ServletConfig config) {
        orderService = (OrderService) config.getServletContext().getAttribute(AppContextConstant.ORDER_SERVICE);
        routService = (RoutService) config.getServletContext().getAttribute(AppContextConstant.ROUT_SERVICE);
        LOGGER.trace("user_account Servlet init");

    }
}
