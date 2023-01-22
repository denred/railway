package com.epam.redkin.railway.web.controller.command.admin;

import com.epam.redkin.railway.model.service.TrainService;
import com.epam.redkin.railway.web.controller.Path;
import com.epam.redkin.railway.web.controller.command.Command;
import com.epam.redkin.railway.appcontext.AppContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TrainRemoveCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(TrainRemoveCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("started");
        TrainService trainService = AppContext.getInstance().getTrainService();
        String trainId = request.getParameter("train_id");
        trainService.removeTrain(Integer.parseInt(trainId));
        LOGGER.info("done");
        return Path.COMMAND_INFO_TRAINS;
    }
}
