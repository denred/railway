package com.epam.redkin.railway.web.controller.command.common;

import com.epam.redkin.railway.model.entity.Order;
import com.epam.redkin.railway.model.entity.User;
import com.epam.redkin.railway.model.service.OrderService;
import com.epam.redkin.railway.model.service.PaginationService;
import com.epam.redkin.railway.web.controller.command.Command;
import com.epam.redkin.railway.appcontext.AppContext;
import com.epam.redkin.railway.web.controller.command.Router;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static com.epam.redkin.railway.util.constants.AppContextConstant.*;
import static com.epam.redkin.railway.web.controller.Path.*;

public class GetUserOrdersCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(GetUserOrdersCommand.class);
    private static final int RECORDS_PER_PAGE = 5;
    private static final int FIRST_VISIBLE_PAGE_LINK = 5;

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("started");

        OrderService orderService = AppContext.getInstance().getOrderService();
        PaginationService paginationService = AppContext.getInstance().getPaginationService();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(SESSION_USER);
        String userId = String.valueOf(user.getUserId());

        int page = paginationService.getPage(request);
        int records = orderService.getCountUserOrders(userId);
        List<Order> orderList = orderService.getUserOrders(userId, (page - 1) * RECORDS_PER_PAGE, RECORDS_PER_PAGE);
        paginationService.setPaginationParameter(request, page, records, RECORDS_PER_PAGE, FIRST_VISIBLE_PAGE_LINK);
        Double successfulOrdersPrice = orderService.getSuccessfulOrdersPrice(userId);

        request.setAttribute(ORDER_LIST, orderList);
        request.setAttribute(SUM, successfulOrdersPrice);

        LOGGER.info("done");
        return Router.forward(PAGE_ORDERS);
    }
}
