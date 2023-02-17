package com.epam.redkin.railway.web.controller.command.admin;

import com.epam.redkin.railway.model.dto.CarriageDTO;
import com.epam.redkin.railway.model.entity.CarriageType;
import com.epam.redkin.railway.model.repository.impl.Constants;
import com.epam.redkin.railway.model.service.CarriageService;
import com.epam.redkin.railway.model.service.PaginationService;
import com.epam.redkin.railway.model.service.SearchService;
import com.epam.redkin.railway.web.controller.Path;
import com.epam.redkin.railway.web.controller.command.Command;
import com.epam.redkin.railway.appcontext.AppContext;
import com.epam.redkin.railway.web.controller.command.Router;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import static com.epam.redkin.railway.util.constants.AppContextConstant.*;

public class CarriageInfoCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(CarriageInfoCommand.class);
    private static final int RECORDS_PER_PAGE = 5;
    private static final int FIRST_VISIBLE_PAGE_LINK = 5;

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("started");
        Router router = new Router();
        router.setRouteType(Router.RouteType.REDIRECT);
        router.setPagePath(Path.COMMAND_INFO_CARRIAGES_PAGE);
        CarriageService carriageService = AppContext.getInstance().getCarriageService();
        PaginationService paginationService = AppContext.getInstance().getPaginationService();
        SearchService searchService = AppContext.getInstance().getSearchService();

        HttpSession session = request.getSession();
        Map<String, String> search = new HashMap<>();
        List<CarriageType> carriageTypeList = new ArrayList<>(EnumSet.allOf(CarriageType.class));
        int page = 1;
        if (request.getParameter(PAGE) != null) {
            page = Integer.parseInt(request.getParameter(PAGE));
        }
        String trainFilter = searchService.getParameter(request, FILTER_TRAIN);
        String carriageFilter = searchService.getParameter(request, FILTER_TYPE_CARRIAGE);

        searchService.addSearch(search, Constants.TRAIN_NUMBER_TABLE, trainFilter);
        searchService.addSearch(search, Constants.TYPE, carriageFilter);

        List<CarriageDTO> carriageDtoList = carriageService.getCarriageDtoListPagination((page - 1) * RECORDS_PER_PAGE,
                RECORDS_PER_PAGE, search);

        int records = carriageService.getRouteListSize(search);

        paginationService.setPaginationParameter(request, page, records, RECORDS_PER_PAGE, FIRST_VISIBLE_PAGE_LINK);

        session.setAttribute(CARRIAGE_TYPE_LIST, carriageTypeList);
        session.setAttribute(FILTER_TRAIN, trainFilter);
        session.setAttribute(FILTER_TYPE_CARRIAGE, carriageFilter);
        session.setAttribute(CARRIAGE_DTO_LIST, carriageDtoList);

        LOGGER.info("done");
        return router;
    }
}