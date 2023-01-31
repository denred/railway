package com.epam.redkin.railway.web.controller.command.admin;

import com.epam.redkin.railway.model.dto.RouteInfoDTO;
import com.epam.redkin.railway.model.service.RouteService;
import com.epam.redkin.railway.util.constants.AppContextConstant;
import com.epam.redkin.railway.web.controller.Path;
import com.epam.redkin.railway.web.controller.command.Command;
import com.epam.redkin.railway.appcontext.AppContext;
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

public class RouteInfoCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(RouteInfoCommand.class);
    private static final int RECORDS_PER_PAGE = 5;
    private static final int FIRST_VISIBLE_PAGE_LINK = 5;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("started");
        RouteService routeService = AppContext.getInstance().getRouteService();
        HttpSession session = request.getSession();
        Map<String, String> search = new HashMap<>();
        int page = 1;
        if (request.getParameter(PAGE) != null) {
            page = Integer.parseInt(request.getParameter(PAGE));
        }
        String filterRouteName = getParameter(request, FILTER_ROUTE_NAME);
        String filterRouteNumber = getParameter(request, FILTER_ROUTE_NUMBER);
        String filterTrainNumber = getParameter(request, FILTER_TRAIN);

        addSearch(search, AppContextConstant.ROUTE_NAME, filterRouteName);
        addSearch(search, AppContextConstant.ROUTE_NUMBER, filterRouteNumber);
        addSearch(search, AppContextConstant.TRAIN_NUMBER, filterTrainNumber);

        List<RouteInfoDTO> routeDtoList = routeService
                .getRouteInfoListWithFilter((page - 1) * RECORDS_PER_PAGE,
                        RECORDS_PER_PAGE, search);
        int recordsCount = routeService.getRouteListSize(search);

        int pagesCount = (int) Math.ceil(recordsCount * 1.0 / RECORDS_PER_PAGE);
        int lastPage = pagesCount;
        pagesCount = Math.min(pagesCount, FIRST_VISIBLE_PAGE_LINK);

        request.setAttribute(PAGE_RECORDS, RECORDS_PER_PAGE);
        request.setAttribute(PAGE_COUNT, pagesCount);
        request.setAttribute(LAST_PAGE, lastPage);
        request.setAttribute(CURRENT_PAGE, page);
        request.setAttribute(ROUTE_DTO_LIST, routeDtoList);
        request.setAttribute(FILTER_ROUTE_NAME, session.getAttribute(FILTER_ROUTE_NAME));
        request.setAttribute(FILTER_ROUTE_NUMBER, session.getAttribute(FILTER_ROUTE_NUMBER));
        request.setAttribute(FILTER_TRAIN, session.getAttribute(FILTER_TRAIN));
        LOGGER.info("done");
        return Path.PAGE_INFO_ROUTE;
    }

    private String getParameter(HttpServletRequest request, String param) {
        String search = request.getParameter(param);
        HttpSession session = request.getSession();
        if (search == null) {
            search = (String) session.getAttribute(param);
        } else {
            session.setAttribute(param, search);
        }
        return search;
    }

    private void addSearch(Map<String, String> search, String key, String value) {
        if (StringUtils.isNoneBlank(value)) {
            search.put(key, value);
        }
    }
}
