package com.epam.redkin.railway.web.controller.command.admin;

import com.epam.redkin.railway.model.entity.Order;
import com.epam.redkin.railway.model.entity.OrderStatus;
import com.epam.redkin.railway.model.exception.IncorrectDataException;
import com.epam.redkin.railway.model.service.OrderService;
import com.epam.redkin.railway.model.validator.OrderValidator;
import com.epam.redkin.railway.web.controller.Path;
import com.epam.redkin.railway.web.controller.command.Command;
import com.epam.redkin.railway.appcontext.AppContext;
import com.epam.redkin.railway.web.controller.command.Router;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import static com.epam.redkin.railway.util.constants.AppContextConstant.*;

public class OrderChangeStatusCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderChangeStatusCommand.class);

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("started");
        Router router = new Router();
        router.setRouteType(Router.RouteType.FORWARD);
        router.setPagePath(Path.PAGE_ADMIN_SET_ORDER_STATUS);
        String orderId = request.getParameter(ORDER_ID);
        String orderStatus = request.getParameter(ORDER_STATUS);
        if (StringUtils.isNoneBlank(orderId, orderStatus)) {
            OrderService orderService = AppContext.getInstance().getOrderService();
            OrderValidator orderValidator = new OrderValidator();
            Order order = Order.builder().build();
            OrderStatus status;
            try {
                status = OrderStatus.valueOf(orderStatus);
            } catch (IllegalArgumentException e) {
                LOGGER.error("Incorrect data entered");
                throw new IncorrectDataException("Incorrect data entered", e);
            }
            orderValidator.isValidOrder(order);
            orderService.updateOrderStatus(Integer.parseInt(orderId), status);
            router.setPagePath(Path.COMMAND_INFO_ORDERS);
            router.setRouteType(Router.RouteType.REDIRECT);
        } else {
            OrderService orderService = AppContext.getInstance().getOrderService();
            List<OrderStatus> orderStatusList = new ArrayList<>(EnumSet.allOf(OrderStatus.class));
            Order order = orderService.getOrderById(Integer.parseInt(orderId));
            request.setAttribute(CURRENT_ORDER, order);
            request.setAttribute(STATUS_LIST, orderStatusList);
        }
        LOGGER.info("done");
        return router;
    }
}
