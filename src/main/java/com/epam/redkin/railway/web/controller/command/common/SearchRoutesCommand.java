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
    private static final int RECORDS_PER_PAGE = 2;
    private static final int FIRST_VISIBLE_PAGE_LINK = 5;

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("started");

        HttpSession session = request.getSession();
        RouteService routeService = AppContext.getInstance().getRouteService();
        PaginationService paginationService = AppContext.getInstance().getPaginationService();
        SearchValidator searchValidator = new SearchValidator();

        String departureStation = StringUtils.defaultString(request.getParameter(DEPARTURE_STATION),
                (String) session.getAttribute(DEPARTURE_STATION));
        String arrivalStation = StringUtils.defaultString(request.getParameter(ARRIVAL_STATION),
                (String) session.getAttribute(ARRIVAL_STATION));
        String startDate = request.getParameter(DEPARTURE_DATE);
        String startTime = request.getParameter(DEPARTURE_TIME);
        String onlyFreeSeats = request.getParameter(STABLE);
        session.setAttribute(STABLE, StringUtils.defaultIfBlank(onlyFreeSeats, "false"));

        LocalDateTime departureDate = StringUtils.isAllBlank(startDate, startTime) ?
                (LocalDateTime) session.getAttribute(DEPARTURE_DATE) : routeService.getDepartureDate(startDate, startTime);

        String errorMessage = searchValidator.isValidSearch(departureStation, arrivalStation);
        if (StringUtils.isNotBlank(errorMessage)) {
            session.setAttribute(ERROR_MESSAGE, errorMessage);
            return Router.builder()
                    .routeType(Router.RouteType.REDIRECT)
                    .pagePath(Path.COMMAND_HOME).build();
        }

        int page = Integer.parseInt(StringUtils.defaultString(request.getParameter(PAGE), "1"));
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
        return Router.builder()
                .routeType(Router.RouteType.REDIRECT)
                .pagePath(Path.COMMAND_SEARCH_ROUTES).build();
    }
}
