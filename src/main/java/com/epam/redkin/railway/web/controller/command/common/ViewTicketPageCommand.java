package com.epam.redkin.railway.web.controller.command.common;

import com.epam.redkin.railway.appcontext.AppContext;
import com.epam.redkin.railway.model.entity.Order;
import com.epam.redkin.railway.model.service.OrderService;
import com.epam.redkin.railway.util.constants.AppContextConstant;
import com.epam.redkin.railway.web.controller.command.Command;
import com.epam.redkin.railway.web.controller.command.Router;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.epam.redkin.railway.web.controller.Path.PAGE_VIEW_TICKET;

public class ViewTicketPageCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(ViewTicketPageCommand.class);

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("Started command execution ViewTicketPageCommand");
        OrderService orderService = AppContext.getInstance().getOrderService();

        HttpSession session = request.getSession();
        String uuid = request.getParameter("orderUuid");
        if(StringUtils.isBlank(uuid)){
            uuid = (String) session.getAttribute("orderUuid");
        }else{
            session.setAttribute("orderUuid", uuid);
        }
        Order order = orderService.getOrderById(uuid);



        session.setAttribute("order", order);

        LOGGER.info("loading page: " + PAGE_VIEW_TICKET);
        return Router.forward(PAGE_VIEW_TICKET);
    }
}
