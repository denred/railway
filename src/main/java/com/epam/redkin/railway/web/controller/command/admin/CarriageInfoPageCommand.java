package com.epam.redkin.railway.web.controller.command.admin;

import com.epam.redkin.railway.web.controller.Path;
import com.epam.redkin.railway.web.controller.command.Command;
import com.epam.redkin.railway.web.controller.command.Router;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CarriageInfoPageCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(CarriageInfoPageCommand.class);

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("Page: " + Path.PAGE_ADMIN_INFO_CARRIAGE);
        return Router.forward(Path.PAGE_ADMIN_INFO_CARRIAGE);
    }
}