package com.epam.redkin.railway.web.controller.command.admin;

import com.epam.redkin.railway.model.entity.Station;
import com.epam.redkin.railway.model.service.StationService;
import com.epam.redkin.railway.model.validator.StationValidator;
import com.epam.redkin.railway.web.controller.Path;
import com.epam.redkin.railway.web.controller.command.Command;
import com.epam.redkin.railway.appcontext.AppContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StationSetCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(StationSetCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("started");
        StationService stationService = AppContext.getInstance().getStationService();
        String forward = Path.PAGE_ADMIN_SET_STATION;
        String stationId = request.getParameter("station_id");
        String stationName = request.getParameter("station");
        if (StringUtils.isNoneBlank(stationName, stationId)) {
            StationValidator stationValidator = new StationValidator();
            Station station = Station.builder().build();
            station.setId(Integer.parseInt(stationId));
            station.setStation(stationName);
            stationValidator.isValidStation(station);
            stationService.updateStation(station);
            forward = Path.COMMAND_INFO_STATIONS;
        } else if (StringUtils.isNoneBlank(stationName)) {
            StationValidator stationValidator = new StationValidator();
            Station station = Station.builder().build();
            station.setStation(stationName);
            stationValidator.isValidStation(station);
            stationService.addStation(station);
            forward = Path.COMMAND_INFO_STATIONS;
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
