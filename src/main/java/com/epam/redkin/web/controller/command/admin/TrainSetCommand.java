package com.epam.redkin.web.controller.command.admin;

import com.epam.redkin.model.entity.Train;
import com.epam.redkin.model.service.TrainService;
import com.epam.redkin.model.validator.TrainValidator;
import com.epam.redkin.web.controller.command.Command;
import com.epam.redkin.web.listener.AppContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.epam.redkin.web.controller.Path.*;

public class TrainSetCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(TrainSetCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("started");
        TrainService trainService = AppContext.getInstance().getTrainService();
        TrainValidator trainValidator = new TrainValidator();
        Train train = new Train();
        String forward = PAGE_ADMIN_SET_TRAIN;
        String trainId = request.getParameter("train_id");
        String trainNumber = request.getParameter("train_number");
        if (StringUtils.isNoneBlank(trainId, trainNumber)) {
            train.setId(Integer.parseInt(trainId));
            train.setNumber(trainNumber);
            trainValidator.isValidTrain(train);
            trainService.updateTrain(train);
            forward = COMMAND_INFO_TRAINS;
        } else if (StringUtils.isNoneBlank(trainNumber)) {
            train.setNumber(trainNumber);
            trainValidator.isValidTrain(train);
            trainService.addTrain(train);
            forward = COMMAND_INFO_TRAINS;
        } else if (StringUtils.isNoneBlank(trainId)){
            train = trainService.getTrainById(Integer.parseInt(trainId));
            request.setAttribute("current_train", train);
        }
        LOGGER.info("done");
        return forward;
    }
}
