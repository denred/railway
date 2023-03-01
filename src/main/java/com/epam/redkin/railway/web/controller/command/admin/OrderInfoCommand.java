package com.epam.redkin.railway.web.controller.command.admin;

import com.epam.redkin.railway.model.entity.Order;
import com.epam.redkin.railway.model.service.OrderService;
import com.epam.redkin.railway.model.service.PaginationService;
import com.epam.redkin.railway.model.service.RouteService;
import com.epam.redkin.railway.web.controller.Path;
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

public class OrderInfoCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderInfoCommand.class);
    private static final int RECORDS_PER_PAGE = 5;
    private static final int FIRST_VISIBLE_PAGE_LINK = 5;
    private final AppContext appContext;

    public OrderInfoCommand(AppContext appContext) {
        this.appContext = appContext;
    }

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("started");
        OrderService orderService = appContext.getOrderService();
        RouteService routeService = appContext.getRouteService();
        PaginationService paginationService = appContext.getPaginationService();
        HttpSession session = request.getSession();
        int page = paginationService.getPage(request);

        int records = orderService.getOrderListSize();

        List<Order> orderList = orderService.getOrderListByCurrentRecordAndRecordsPerPage(
                (page - 1) * RECORDS_PER_PAGE,
                RECORDS_PER_PAGE * page);

        for (Order order : orderList) {
            order.setRouteName(routeService
                    .getRouteInfoById(order.getRouteId())
                    .getRoutName());
        }

        paginationService.setPaginationParameter(request, page, records, RECORDS_PER_PAGE, FIRST_VISIBLE_PAGE_LINK);

        session.setAttribute(ORDER_LIST, orderList);
        LOGGER.info("done");
        return Router.forward(Path.PAGE_ADMIN_INFO_ORDER);
    }
}
