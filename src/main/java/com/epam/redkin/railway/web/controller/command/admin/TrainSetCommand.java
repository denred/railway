package com.epam.redkin.railway.web.controller.command.admin;

import com.epam.redkin.railway.model.entity.Train;
import com.epam.redkin.railway.model.service.TrainService;
import com.epam.redkin.railway.model.validator.TrainValidator;
import com.epam.redkin.railway.web.controller.Path;
import com.epam.redkin.railway.web.controller.command.Command;
import com.epam.redkin.railway.appcontext.AppContext;
import com.epam.redkin.railway.web.controller.command.Router;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.epam.redkin.railway.util.constants.AppContextConstant.*;

public class TrainSetCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(TrainSetCommand.class);

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("started");
        Router router = new Router();
        router.setRouteType(Router.RouteType.FORWARD);
        router.setPagePath(Path.PAGE_ADMIN_SET_TRAIN);
        TrainService trainService = AppContext.getInstance().getTrainService();
        TrainValidator trainValidator = new TrainValidator();
        String trainId = request.getParameter(TRAIN_ID);
        String trainNumber = request.getParameter(TRAIN_NUMBER);
        Train train = Train.builder().build();
        if (StringUtils.isNoneBlank(trainId, trainNumber)) {
            train.setId(Integer.parseInt(trainId));
            train.setNumber(trainNumber);
            trainValidator.isValidTrain(train);
            trainService.updateTrain(train);
            router.setRouteType(Router.RouteType.REDIRECT);
            router.setPagePath(Path.COMMAND_INFO_TRAINS);
        } else if (StringUtils.isNoneBlank(trainNumber)) {
            train.setNumber(trainNumber);
            trainValidator.isValidTrain(train);
            trainService.addTrain(train);
            router.setRouteType(Router.RouteType.REDIRECT);
            router.setPagePath(Path.COMMAND_INFO_TRAINS);
        } else if (StringUtils.isNoneBlank(trainId)) {
            train = trainService.getTrainById(Integer.parseInt(trainId));
            request.setAttribute(CURRENT_TRAIN, train);
        }
        LOGGER.info("done");
        return router;
    }
}
