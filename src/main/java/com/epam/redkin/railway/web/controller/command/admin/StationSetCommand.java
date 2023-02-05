package com.epam.redkin.railway.web.controller.command.admin;

import com.epam.redkin.railway.model.entity.Station;
import com.epam.redkin.railway.model.service.StationService;
import com.epam.redkin.railway.model.validator.StationValidator;
import com.epam.redkin.railway.web.controller.Path;
import com.epam.redkin.railway.web.controller.command.Command;
import com.epam.redkin.railway.appcontext.AppContext;
import com.epam.redkin.railway.web.controller.command.Router;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.epam.redkin.railway.model.repository.impl.Constants.STATION;
import static com.epam.redkin.railway.util.constants.AppContextConstant.STATION_ID;

public class StationSetCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(StationSetCommand.class);

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("started");
        Router router = new Router();
        router.setRouteType(Router.RouteType.FORWARD);
        router.setPagePath(Path.PAGE_ADMIN_SET_STATION);
        StationService stationService = AppContext.getInstance().getStationService();
        String stationId = request.getParameter(STATION_ID);
        String stationName = request.getParameter(STATION);
        if (StringUtils.isNoneBlank(stationName, stationId)) {
            StationValidator stationValidator = new StationValidator();
            Station station = Station.builder().build();
            station.setId(Integer.parseInt(stationId));
            station.setStation(stationName);
            stationValidator.isValidStation(station);
            stationService.updateStation(station);
            router.setRouteType(Router.RouteType.REDIRECT);
            router.setPagePath(Path.COMMAND_INFO_STATIONS);
        } else if (StringUtils.isNoneBlank(stationName)) {
            StationValidator stationValidator = new StationValidator();
            Station station = Station.builder().build();
            station.setStation(stationName);
            stationValidator.isValidStation(station);
            stationService.addStation(station);
            router.setRouteType(Router.RouteType.REDIRECT);
            router.setPagePath(Path.COMMAND_INFO_STATIONS);
        } else {
            if (StringUtils.isNoneBlank(stationId)) {
                Station station = stationService.getStationById(Integer.parseInt(stationId));
                stationId = String.valueOf(station.getId());
                stationName = station.getStation();
            }
            request.setAttribute(STATION_ID, stationId);
            request.setAttribute(STATION, stationName);
        }
        LOGGER.info("done");
        return router;
    }
}
