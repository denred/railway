package com.epam.redkin.web.controller.command.admin;

import com.epam.redkin.model.entity.Station;
import com.epam.redkin.model.service.StationService;
import com.epam.redkin.model.validator.StationValidator;
import com.epam.redkin.web.controller.command.Command;
import com.epam.redkin.web.listener.AppContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.epam.redkin.web.controller.Path.COMMAND_INFO_STATIONS;
import static com.epam.redkin.web.controller.Path.PAGE_ADMIN_SET_STATION;

public class StationSetCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(StationSetCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("started");
        StationService stationService = AppContext.getInstance().getStationService();
        String forward = PAGE_ADMIN_SET_STATION;
        String stationId = request.getParameter("station_id");
        String stationName = request.getParameter("station");
        if (StringUtils.isNoneBlank(stationName, stationId)) {
            StationValidator stationValidator = new StationValidator();
            Station station = new Station();
            station.setId(Integer.parseInt(stationId));
            station.setStation(stationName);
            stationValidator.isValidStation(station);
            stationService.updateStation(station);
            forward = COMMAND_INFO_STATIONS;
        } else if (StringUtils.isNoneBlank(stationName)) {
            StationValidator stationValidator = new StationValidator();
            Station station = new Station();
            station.setStation(stationName);
            stationValidator.isValidStation(station);
            stationService.addStation(station);
            forward = COMMAND_INFO_STATIONS;
        } else {
            if (StringUtils.isNoneBlank(stationId)) {
                Station station = stationService.getStationById(Integer.parseInt(stationId));
                stationId = String.valueOf(station.getId());
                stationName = station.getStation();
            }
            request.setAttribute("station_id", stationId);
            request.setAttribute("station", stationName);
        }
        LOGGER.info("done");
        return forward;
    }
}
