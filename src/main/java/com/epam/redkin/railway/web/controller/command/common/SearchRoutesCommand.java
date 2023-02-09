package com.epam.redkin.railway.web.controller.command.common;

import com.epam.redkin.railway.model.dto.RouteOrderDTO;
import com.epam.redkin.railway.model.service.PaginationService;
import com.epam.redkin.railway.model.service.RouteService;
import com.epam.redkin.railway.model.validator.SearchValidator;
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

import java.time.LocalDateTime;
import java.util.*;

import static com.epam.redkin.railway.util.constants.AppContextConstant.*;

public class SearchRoutesCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(SearchRoutesCommand.class);
    private static final int RECORDS_PER_PAGE = 3;
    private static final int FIRST_VISIBLE_PAGE_LINK = 5;

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("started");
        Router router = new Router();
        router.setRouteType(Router.RouteType.REDIRECT);
        router.setPagePath(Path.COMMAND_SEARCH_ROUTES);

        RouteService routeService = AppContext.getInstance().getRouteService();
        SearchValidator searchValidator = new SearchValidator();
        HttpSession session = request.getSession();
        PaginationService paginationService = AppContext.getInstance().getPaginationService();

        String departureStation = request.getParameter(DEPARTURE_STATION);
        String arrivalStation = request.getParameter(ARRIVAL_STATION);
        String startDate = request.getParameter(DEPARTURE_DATE);
        String startTime = request.getParameter(DEPARTURE_TIME);
        String onlyFreeSeats = request.getParameter("stable");
        session.setAttribute("stable", onlyFreeSeats);
        int page = request.getParameter(PAGE) != null ? Integer.parseInt(request.getParameter(PAGE)) : 1;

        if (StringUtils.isBlank(departureStation)) {
            departureStation = (String) session.getAttribute(DEPARTURE_STATION);
        }
        if (StringUtils.isBlank(arrivalStation)) {
            arrivalStation = (String) session.getAttribute(ARRIVAL_STATION);
        }
        LocalDateTime departureDate;
        if (StringUtils.isAllBlank(startDate, startTime)) {
            departureDate = (LocalDateTime) session.getAttribute(DEPARTURE_DATE);
        } else {
            departureDate = routeService.getDepartureDate(startDate, startTime);
        }
        String errorMessage = searchValidator.isValidSearch(departureStation, arrivalStation);
        if (StringUtils.isNoneBlank(errorMessage)) {
            router.setRouteType(Router.RouteType.REDIRECT);
            router.setPagePath(Path.COMMAND_HOME);
            session.setAttribute(ERROR_MESSAGE, errorMessage);
            return router;
        }
        List<RouteOrderDTO> routeOrderDTOSPagination = routeService.getSearchRoutePagination(departureStation, arrivalStation,
                departureDate, (page - 1) * RECORDS_PER_PAGE, RECORDS_PER_PAGE * page, onlyFreeSeats);
        List<RouteOrderDTO> routeOrderDTOList = routeService.getRouteOrderDtoList(departureStation, arrivalStation, departureDate, onlyFreeSeats);
        int records = routeOrderDTOList.size();
        paginationService.setPaginationParameter(request, page, records, RECORDS_PER_PAGE, FIRST_VISIBLE_PAGE_LINK);

        session.removeAttribute(ERROR_MESSAGE);
        session.setAttribute(ROUTE_ORDER_DTO_LIST, routeOrderDTOSPagination);
        session.setAttribute(DEPARTURE_STATION, departureStation);
        session.setAttribute(ARRIVAL_STATION, arrivalStation);
        session.setAttribute(DEPARTURE_DATE, departureDate);

        LOGGER.info("done");
        return router;
    }
}
