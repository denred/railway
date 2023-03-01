package com.epam.redkin.railway.web.controller.command.admin;

import com.epam.redkin.railway.model.entity.Order;
import com.epam.redkin.railway.model.entity.OrderStatus;
import com.epam.redkin.railway.util.constants.AppContextConstant;
import com.epam.redkin.railway.web.controller.command.Command;
import com.epam.redkin.railway.web.controller.command.Router;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import static com.epam.redkin.railway.util.constants.AppContextConstant.CURRENT_ORDER;
import static com.epam.redkin.railway.util.constants.AppContextConstant.STATUS_LIST;
import static com.epam.redkin.railway.web.controller.Path.PAGE_ORDER_DETAIL;

public class OrderDetailGetPage implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderDetailGetPage.class);

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("loading page: " + PAGE_ORDER_DETAIL);
        HttpSession session = request.getSession();
        session.setAttribute("index", request.getParameter("order"));
        List<OrderStatus> orderStatusList = new ArrayList<>(EnumSet.allOf(OrderStatus.class));
        request.setAttribute(STATUS_LIST, orderStatusList);
        return Router.forward(PAGE_ORDER_DETAIL);
    }
}
