package com.epam.redkin.railway.web.controller.command.admin;

import com.epam.redkin.railway.model.dto.MappingInfoDTO;
import com.epam.redkin.railway.model.service.RouteMappingService;
import com.epam.redkin.railway.web.controller.Path;
import com.epam.redkin.railway.web.controller.command.Command;
import com.epam.redkin.railway.web.controller.command.Router;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static com.epam.redkin.railway.util.constants.AppContextConstant.*;

public class RouteMappingCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(RouteMappingCommand.class);
    private final RouteMappingService routeMappingService;

    public RouteMappingCommand(RouteMappingService routeMappingService) {
        this.routeMappingService = routeMappingService;
    }

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("started");

        HttpSession session = request.getSession();
        String routeId = request.getParameter(ROUTE_ID);
        if (StringUtils.isBlank(routeId)) {
            routeId = (String) session.getAttribute(ROUTE_ID);
        }


        if (StringUtils.isNoneBlank(routeId)) {
            List<MappingInfoDTO> mappingInfo = routeMappingService.getRouteMappingInfoDTOs(Integer.parseInt(routeId));
            int lastMappingStation = routeMappingService.getLastStation(mappingInfo);
            request.setAttribute(ROUTE_MAPPING_LIST, mappingInfo);
            request.setAttribute(LAST_STATION, lastMappingStation);
            session.setAttribute(ROUTE_ID, routeId);
        }

        LOGGER.info("done");
        return Router.forward(Path.PAGE_ADMIN_ROUTE_DETAIL);
    }
}
