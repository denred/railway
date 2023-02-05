package com.epam.redkin.railway.web.controller.command.admin;

import com.epam.redkin.railway.model.dto.MappingInfoDTO;
import com.epam.redkin.railway.model.service.RouteMappingService;
import com.epam.redkin.railway.web.controller.Path;
import com.epam.redkin.railway.web.controller.command.Command;
import com.epam.redkin.railway.appcontext.AppContext;
import com.epam.redkin.railway.web.controller.command.Router;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static com.epam.redkin.railway.model.repository.impl.Constants.STATION_ID;
import static com.epam.redkin.railway.util.constants.AppContextConstant.*;

public class RouteMappingCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(RouteMappingCommand.class);

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("started");
        Router router = new Router();
        router.setRouteType(Router.RouteType.FORWARD);
        router.setPagePath(Path.PAGE_ADMIN_ROUTE_DETAIL);
        RouteMappingService routeMappingService = AppContext.getInstance().getRouteMappingService();
        String routeId = request.getParameter(ROUTE_ID);
        String stationId = request.getParameter(STATION_ID);
        if (StringUtils.isNoneBlank(routeId)) {
            List<MappingInfoDTO> mappingInfo = routeMappingService.getMappingInfoDtoListByRouteId(Integer.parseInt(routeId));
            int lastMappingStation = routeMappingService.getLastStation(mappingInfo);
            request.setAttribute(ROUTE_MAPPING_LIST, mappingInfo);
            request.setAttribute(LAST_STATION, lastMappingStation);
        }
        request.setAttribute(STATION_ID, stationId);
        request.setAttribute(ROUTE_ID, routeId);

        LOGGER.info("done");
        return router;
    }
}
