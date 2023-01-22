package com.epam.redkin.railway.web.controller.command.admin;

import com.epam.redkin.railway.model.service.CarriageService;
import com.epam.redkin.railway.web.controller.Path;
import com.epam.redkin.railway.web.controller.command.Command;
import com.epam.redkin.railway.appcontext.AppContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CarriageRemoveCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(CarriageRemoveCommand.class);
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("started");
        CarriageService carriageService = AppContext.getInstance().getCarriageService();
        String carriageId = request.getParameter("car_id");
        carriageService.removeCar(Integer.parseInt(carriageId));
        LOGGER.info("done");
        return Path.COMMAND_INFO_CARRIAGES;
    }
}
