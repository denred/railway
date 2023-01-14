package com.epam.redkin.web.controller.command.admin;

import com.epam.redkin.model.service.CarriageService;
import com.epam.redkin.web.controller.command.Command;
import com.epam.redkin.web.listener.AppContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.epam.redkin.web.controller.Path.COMMAND_INFO_CARRIAGES;

public class CarriageRemoveCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(CarriageRemoveCommand.class);
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("started");
        CarriageService carriageService = AppContext.getInstance().getCarriageService();
        String carriageId = request.getParameter("car_id");
        carriageService.removeCar(Integer.parseInt(carriageId));
        LOGGER.info("done");
        return COMMAND_INFO_CARRIAGES;
    }
}
