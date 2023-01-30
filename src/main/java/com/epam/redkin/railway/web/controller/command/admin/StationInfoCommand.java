package com.epam.redkin.railway.web.controller.command.admin;

import com.epam.redkin.railway.model.entity.Station;
import com.epam.redkin.railway.model.service.StationService;
import com.epam.redkin.railway.web.controller.Path;
import com.epam.redkin.railway.web.controller.command.Command;
import com.epam.redkin.railway.appcontext.AppContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static com.epam.redkin.railway.util.constants.AppContextConstant.*;

public class StationInfoCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(StationInfoCommand.class);
    private static final int RECORDS_PER_PAGE = 5;
    private static final int FIRST_VISIBLE_PAGE_LINK = 5;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("started");
        StationService stationService = AppContext.getInstance().getStationService();
        HttpSession session = request.getSession();
        String search = request.getParameter(FILTER_STATION);
        if (search == null) {
            search = (String) session.getAttribute(FILTER_STATION);
        } else {
            session.setAttribute(FILTER_STATION, search);
        }

        int page = 1;
        if (request.getParameter(PAGE) != null) {
            page = Integer.parseInt(request.getParameter(PAGE));
        }
        List<Station> stationList = stationService.getStationList((page - 1) * RECORDS_PER_PAGE, RECORDS_PER_PAGE, search);
        int recordsCount = stationService.getStationListSize(search);
        int pagesCount = (int) Math.ceil(recordsCount * 1.0 / RECORDS_PER_PAGE);
        int lastPage = pagesCount;
        pagesCount = Math.min(pagesCount, FIRST_VISIBLE_PAGE_LINK);

        request.setAttribute(PAGE_RECORDS, RECORDS_PER_PAGE);
        request.setAttribute(STATION_LIST, stationList);
        request.setAttribute(PAGE_COUNT, pagesCount);
        request.setAttribute(LAST_PAGE, lastPage);
        request.setAttribute(CURRENT_PAGE, page);
        request.setAttribute(FILTER_STATION, session.getAttribute(FILTER_STATION));
        LOGGER.info("done");
        return Path.PAGE_ADMIN_INFO_STATION;
    }
}
