package com.epam.redkin.railway.web.controller.command.admin;

import com.epam.redkin.railway.model.entity.Train;
import com.epam.redkin.railway.model.repository.impl.Constants;
import com.epam.redkin.railway.model.service.PaginationService;
import com.epam.redkin.railway.model.service.SearchService;
import com.epam.redkin.railway.model.service.TrainService;
import com.epam.redkin.railway.model.validator.TrainValidator;
import com.epam.redkin.railway.web.controller.Path;
import com.epam.redkin.railway.web.controller.command.Command;
import com.epam.redkin.railway.appcontext.AppContext;
import com.epam.redkin.railway.web.controller.command.Router;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.epam.redkin.railway.util.constants.AppContextConstant.*;

public class TrainInfoCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(TrainInfoCommand.class);
    private final AppContext appContext;
    private static final int RECORDS_PER_PAGE = 5;
    private static final int FIRST_VISIBLE_PAGE_LINK = 5;

    public TrainInfoCommand(AppContext appContext) {
        this.appContext = appContext;
    }

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("TrainInfoCommand execution started");

        HttpSession session = request.getSession();
        TrainValidator trainValidator = appContext.getTrainValidator();

        SearchService searchService = appContext.getSearchService();

        String trainFilter = searchService.getParameter(request, FILTER_TRAIN);
        if (StringUtils.isNotBlank(trainFilter)) {
            String errorMessage = trainValidator.isValidTrain(Train.builder().number(trainFilter).build());
            if (StringUtils.isNotBlank(errorMessage)) {
                session.setAttribute(ERROR_MESSAGE, errorMessage);
                return Router.redirect(Path.COMMAND_INFO_TRAINS_PAGE);
            }
        }

        Map<String, String> search = new HashMap<>();
        searchService.addSearch(search, Constants.NUMBER, trainFilter);

        TrainService trainService = appContext.getTrainService();
        int records = trainService.getTrainListSize(search);

        PaginationService paginationService = appContext.getPaginationService();
        int page = paginationService.getPage(request);

        List<Train> trainList = trainService.getTrainListWithPagination(
                (page - 1) * RECORDS_PER_PAGE, RECORDS_PER_PAGE, search);

        paginationService.setPaginationParameter(request, page, records, RECORDS_PER_PAGE, FIRST_VISIBLE_PAGE_LINK);

        session.removeAttribute(ERROR_MESSAGE);
        session.setAttribute(TRAIN_LIST, trainList);
        LOGGER.info("TrainInfoCommand execution done");
        return Router.redirect(Path.COMMAND_INFO_TRAINS_PAGE);
    }
}