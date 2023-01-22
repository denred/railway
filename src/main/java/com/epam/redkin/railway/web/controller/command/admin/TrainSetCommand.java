package com.epam.redkin.railway.web.controller.command.admin;

import com.epam.redkin.railway.model.entity.Train;
import com.epam.redkin.railway.model.service.TrainService;
import com.epam.redkin.railway.model.validator.TrainValidator;
import com.epam.redkin.railway.web.controller.Path;
import com.epam.redkin.railway.web.controller.command.Command;
import com.epam.redkin.railway.appcontext.AppContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TrainSetCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(TrainSetCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("started");
        TrainService trainService = AppContext.getInstance().getTrainService();
        TrainValidator trainValidator = new TrainValidator();
        String forward = Path.PAGE_ADMIN_SET_TRAIN;
        String trainId = request.getParameter("train_id");
        String trainNumber = request.getParameter("train_number");
        Train train = Train.builder().build();
        if (StringUtils.isNoneBlank(trainId, trainNumber)) {
            train.setId(Integer.parseInt(trainId));
            train.setNumber(trainNumber);
            trainValidator.isValidTrain(train);
            trainService.updateTrain(train);
            forward = Path.COMMAND_INFO_TRAINS;
        } else if (StringUtils.isNoneBlank(trainNumber)) {
            train.setNumber(trainNumber);
            trainValidator.isValidTrain(train);
            trainService.addTrain(train);
            forward = Path.COMMAND_INFO_TRAINS;
        } else if (StringUtils.isNoneBlank(trainId)){
            train = trainService.getTrainById(Integer.parseInt(trainId));
            request.setAttribute("current_train", train);
        }
        LOGGER.info("done");
        return forward;
    }
}
