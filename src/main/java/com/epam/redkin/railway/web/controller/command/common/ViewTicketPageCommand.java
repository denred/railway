package com.epam.redkin.railway.web.controller.command.common;

import com.epam.redkin.railway.web.controller.command.Command;
import com.epam.redkin.railway.web.controller.command.Router;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.epam.redkin.railway.web.controller.Path.PAGE_VIEW_TICKET;

public class ViewTicketPageCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(ViewTicketPageCommand.class);

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("loading page: " + PAGE_VIEW_TICKET);
        return Router.forward(PAGE_VIEW_TICKET);
    }
}
