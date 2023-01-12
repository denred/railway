package com.epam.redkin.web.controller.command.common;

import com.epam.redkin.model.entity.Order;
import com.epam.redkin.service.OrderService;
import com.epam.redkin.service.RouteService;
import com.epam.redkin.web.controller.command.Command;
import com.epam.redkin.web.listener.AppContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static com.epam.redkin.web.controller.Path.*;

public class OrderCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderCommand.class);
    private static final int RECORDS_PER_PAGE = 5;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.debug("OrderCommand started");
        RouteService routeService = AppContext.getInstance().getRouteService();
        OrderService orderService = AppContext.getInstance().getOrderService();
        String userId = request.getParameter(USER_ID);
        int noOfRecords = orderService.getOrderListSizeByUserId(userId);
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / RECORDS_PER_PAGE);
        int page = 1;
        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }
        List<Order> orderList = orderService.getOrderListByUserIdAndByCurrentRecordAndRecordsPerPage(
                userId,
                (page - 1) * RECORDS_PER_PAGE,
                RECORDS_PER_PAGE * page);
        for (Order order : orderList) {
            order.setRouteName(routeService.getRoutById(order.getRouteId()).getRouteName());
        }
        Double priceOfSuccessfulOrders = orderService.getPriceOfSuccessfulOrders(Integer.parseInt(userId));
        request.setAttribute("recordsPerPage", RECORDS_PER_PAGE);
        request.setAttribute("noOfPages", noOfPages);
        request.setAttribute("currentPage", page);
        request.setAttribute("user_id", userId);
        request.setAttribute("order_list", orderList);
        request.setAttribute("sum", priceOfSuccessfulOrders);
        HttpSession session = request.getSession();
        request.setAttribute("lang", session.getAttribute(LOCALE));
        LOGGER.debug("OrderCommand done");
        return PAGE_ORDERS;
    }
}
