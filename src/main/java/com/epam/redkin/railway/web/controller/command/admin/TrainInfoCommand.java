package com.epam.redkin.railway.web.controller.command.admin;

import com.epam.redkin.railway.model.entity.Train;
import com.epam.redkin.railway.model.service.TrainService;
import com.epam.redkin.railway.web.controller.Path;
import com.epam.redkin.railway.web.controller.command.Command;
import com.epam.redkin.railway.appcontext.AppContext;
import com.epam.redkin.railway.web.controller.command.Router;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static com.epam.redkin.railway.util.constants.AppContextConstant.*;

public class TrainInfoCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(TrainInfoCommand.class);
    private static final int RECORDS_PER_PAGE = 5;

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("started");
        Router router = new Router();
        router.setRouteType(Router.RouteType.FORWARD);
        router.setPagePath(Path.PAGE_ADMIN_INFO_TRAIN);
        TrainService trainService = AppContext.getInstance().getTrainService();
        int page = 1;
        if (request.getParameter(PAGE) != null) {
            page = Integer.parseInt(request.getParameter(PAGE));
        }
        int noOfRecords = trainService.getTrainListSize();
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / RECORDS_PER_PAGE);
        List<Train> trainList = trainService.getTrainListBySetRecords(
                (page - 1) * RECORDS_PER_PAGE,
                RECORDS_PER_PAGE * page);
        request.setAttribute(PAGE_RECORDS, RECORDS_PER_PAGE);
        request.setAttribute(PAGE_COUNT, noOfPages);
        request.setAttribute(CURRENT_PAGE, page);
        request.setAttribute(TRAIN_LIST, trainList);
        LOGGER.info("done");
        return router;
    }
}
