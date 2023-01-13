package com.epam.redkin.web.controller.command.common;

import com.epam.redkin.model.entity.Order;
import com.epam.redkin.model.entity.User;
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

public class GetUserOrdersCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(GetUserOrdersCommand.class);
    private static final int RECORDS_PER_PAGE = 5;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("started");
        RouteService routeService = AppContext.getInstance().getRouteService();
        OrderService orderService = AppContext.getInstance().getOrderService();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(SESSION_USER);
        String userId = String.valueOf(user.getUserId());
        LOGGER.info(userId + "");
        int noOfRecords = orderService.getOrderListSizeByUserId(userId);
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / RECORDS_PER_PAGE);
        int page = 1;
        if (request.getParameter(PAGE) != null) {
            page = Integer.parseInt(request.getParameter(PAGE));
        }
        List<Order> orderList = orderService.getOrderListByUserIdAndByCurrentRecordAndRecordsPerPage(
                userId,
                (page - 1) * RECORDS_PER_PAGE,
                RECORDS_PER_PAGE * page);
        for (Order order : orderList) {
            order.setRouteName(routeService.getRoutById(order.getRouteId()).getRouteName());
        }
        Double priceOfSuccessfulOrders = orderService.getPriceOfSuccessfulOrders(Integer.parseInt(userId));
        request.setAttribute(PAGE_RECORDS, RECORDS_PER_PAGE);
        request.setAttribute(PAGE_COUNT, noOfPages);
        request.setAttribute(CURRENT_PAGE, page);
        request.setAttribute(USER_ID, userId);
        request.setAttribute(ORDER_LIST, orderList);
        request.setAttribute(SUM, priceOfSuccessfulOrders);
        request.setAttribute(LANGUAGE, session.getAttribute(LOCALE));
        LOGGER.info("done");
        return PAGE_ORDERS;
    }
}
