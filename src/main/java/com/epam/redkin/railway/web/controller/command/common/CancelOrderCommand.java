package com.epam.redkin.railway.web.controller.command.common;

import com.epam.redkin.railway.model.service.OrderService;
import com.epam.redkin.railway.web.controller.command.Command;
import com.epam.redkin.railway.appcontext.AppContext;
import com.epam.redkin.railway.web.controller.command.Router;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.epam.redkin.railway.util.constants.AppContextConstant.ORDER_ID;
import static com.epam.redkin.railway.web.controller.Path.PAGE_HOME;

public class CancelOrderCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(CancelOrderCommand.class);

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("started");
        Router router = new Router();
        router.setRouteType(Router.RouteType.FORWARD);
        router.setPagePath(PAGE_HOME);
        OrderService orderService = AppContext.getInstance().getOrderService();
        String orderId = request.getParameter(ORDER_ID);
        orderService.cancelOrder(Integer.parseInt(orderId));
        LOGGER.info("done");
        return router;
    }
}
