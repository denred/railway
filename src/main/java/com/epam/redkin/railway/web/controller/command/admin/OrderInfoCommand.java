package com.epam.redkin.railway.web.controller.command.admin;

import com.epam.redkin.railway.model.entity.Order;
import com.epam.redkin.railway.model.service.OrderService;
import com.epam.redkin.railway.model.service.RouteService;
import com.epam.redkin.railway.util.constants.AppContextConstant;
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
    private static final int RECORDS_PER_PAGE = 2;

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("started");
        Router router = new Router();
        router.setRouteType(Router.RouteType.FORWARD);
        router.setPagePath(Path.PAGE_ADMIN_INFO_ORDER);
        OrderService orderService = AppContext.getInstance().getOrderService();
        RouteService routeService = AppContext.getInstance().getRouteService();
        int page = 1;
        if (request.getParameter(PAGE) != null) {
            page = Integer.parseInt(request.getParameter(PAGE));
        }
        int noOfRecords = orderService.getOrderListSize();
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / RECORDS_PER_PAGE);
        List<Order> orderList = orderService.getOrderListByCurrentRecordAndRecordsPerPage(
                (page - 1) * RECORDS_PER_PAGE,
                RECORDS_PER_PAGE * page);
        for (Order order : orderList) {
            order.setRouteName(routeService
                    .getRouteInfoById(order.getRouteId())
                    .getRoutName());
        }
        request.setAttribute(PAGE_RECORDS, RECORDS_PER_PAGE);
        request.setAttribute(PAGE_COUNT, noOfPages);
        request.setAttribute(CURRENT_PAGE, page);
        request.setAttribute(ORDER_LIST, orderList);
        HttpSession session = request.getSession();
        request.setAttribute(LANGUAGE, session.getAttribute(AppContextConstant.LOCALE));
        LOGGER.info("done");
        return router;
    }
}
