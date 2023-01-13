package com.epam.redkin.web.controller.command.admin;

import com.epam.redkin.model.dto.RouteInfoDTO;
import com.epam.redkin.model.service.RouteService;
import com.epam.redkin.web.controller.command.Command;
import com.epam.redkin.web.listener.AppContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static com.epam.redkin.web.controller.Path.*;

public class InfoRouteCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(InfoRouteCommand.class);
    private static final int RECORDS_PER_PAGE = 5;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("started");
        RouteService routeService = AppContext.getInstance().getRouteService();
        int page = 1;
        if (request.getParameter(PAGE) != null)
            page = Integer.parseInt(request.getParameter(PAGE));
        List<RouteInfoDTO> routeDtoList = routeService.getRouteListByCurrentRecordAndRecordsPerPage(
                (page - 1) * RECORDS_PER_PAGE,
                RECORDS_PER_PAGE * page);
        int noOfRecords = routeService.getRouteListSize();
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / RECORDS_PER_PAGE);
        HttpSession session = request.getSession();
        LOGGER.debug(RECORDS_PER_PAGE + " | " + noOfPages + " | " + routeDtoList + " _ " + session.getAttribute(LOCALE));
        request.setAttribute(PAGE_RECORDS, RECORDS_PER_PAGE);
        request.setAttribute(PAGE_COUNT, noOfPages);
        request.setAttribute(CURRENT_PAGE, page);
        request.setAttribute(ROUTE_DTO_LIST, routeDtoList);
        request.setAttribute(LANGUAGE, session.getAttribute(LOCALE));
        LOGGER.info("done");
        return PAGE_INFO_ROUTE;
    }
}
