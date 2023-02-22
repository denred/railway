package com.epam.redkin.railway.web.controller.command.admin;

import com.epam.redkin.railway.web.controller.Path;
import com.epam.redkin.railway.web.controller.command.Command;
import com.epam.redkin.railway.web.controller.command.Router;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class TrainInfoPageCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(TrainInfoPageCommand.class);

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("TrainInfoPageCommand execution");
        return Router.forward(Path.PAGE_ADMIN_INFO_TRAIN);
    }
}