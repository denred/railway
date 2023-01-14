package com.epam.redkin.web.controller.command.admin;

import com.epam.redkin.model.service.TrainService;
import com.epam.redkin.web.controller.command.Command;
import com.epam.redkin.web.listener.AppContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.epam.redkin.web.controller.Path.COMMAND_INFO_TRAINS;

public class TrainRemoveCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(TrainRemoveCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("started");
        TrainService trainService = AppContext.getInstance().getTrainService();
        String trainId = request.getParameter("train_id");
        trainService.removeTrain(Integer.parseInt(trainId));
        LOGGER.info("done");
        return COMMAND_INFO_TRAINS;
    }
}
