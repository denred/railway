package com.epam.redkin.railway.web.controller.command.admin;

import com.epam.redkin.railway.model.entity.Train;
import com.epam.redkin.railway.model.repository.impl.Constants;
import com.epam.redkin.railway.model.service.PaginationService;
import com.epam.redkin.railway.model.service.SearchService;
import com.epam.redkin.railway.model.service.TrainService;
import com.epam.redkin.railway.web.controller.Path;
import com.epam.redkin.railway.web.controller.command.Command;
import com.epam.redkin.railway.appcontext.AppContext;
import com.epam.redkin.railway.web.controller.command.Router;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.epam.redkin.railway.util.constants.AppContextConstant.*;

public class TrainInfoCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(TrainInfoCommand.class);
    private static final int RECORDS_PER_PAGE = 5;
    private static final int FIRST_VISIBLE_PAGE_LINK = 5;

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("started");
        Router router = new Router();
        router.setRouteType(Router.RouteType.REDIRECT);
        router.setPagePath(Path.COMMAND_INFO_TRAINS_PAGE);

        TrainService trainService = AppContext.getInstance().getTrainService();
        PaginationService paginationService = AppContext.getInstance().getPaginationService();
        SearchService searchService = AppContext.getInstance().getSearchService();
        Map<String, String> search = new HashMap<>();
        HttpSession session = request.getSession();

        int page = 1;
        if (request.getParameter(PAGE) != null) {
            page = Integer.parseInt(request.getParameter(PAGE));
        }
        String trainFilter = searchService.getParameter(request, FILTER_TRAIN);
        searchService.addSearch(search, Constants.NUMBER, trainFilter);

        int records = trainService.getTrainListSize(search);
        List<Train> trainList = trainService.getTrainListWithPagination((page - 1) * RECORDS_PER_PAGE, RECORDS_PER_PAGE, search);
        paginationService.setPaginationParameter(request, page, records, RECORDS_PER_PAGE, FIRST_VISIBLE_PAGE_LINK);

        session.setAttribute(FILTER_TRAIN, trainFilter);
        session.setAttribute(TRAIN_LIST, trainList);
        LOGGER.info("done");
        return router;
    }
}
