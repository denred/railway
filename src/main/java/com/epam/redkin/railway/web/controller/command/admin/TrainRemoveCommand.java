package com.epam.redkin.railway.web.controller.command.admin;

import com.epam.redkin.railway.model.service.TrainService;
import com.epam.redkin.railway.web.controller.Path;
import com.epam.redkin.railway.web.controller.command.Command;
import com.epam.redkin.railway.appcontext.AppContext;
import com.epam.redkin.railway.web.controller.command.Router;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.epam.redkin.railway.util.constants.AppContextConstant.TRAIN_ID;

public class TrainRemoveCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(TrainRemoveCommand.class);

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("started");
        Router router = new Router();
        router.setRouteType(Router.RouteType.REDIRECT);
        router.setPagePath(Path.COMMAND_INFO_TRAINS);
        TrainService trainService = AppContext.getInstance().getTrainService();
        String trainId = request.getParameter(TRAIN_ID);
        trainService.removeTrain(Integer.parseInt(trainId));
        LOGGER.info("done");
        return router;
    }
}
