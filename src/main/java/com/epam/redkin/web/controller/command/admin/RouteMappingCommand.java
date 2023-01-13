package com.epam.redkin.web.controller.command.admin;

import com.epam.redkin.model.dto.MappingInfoDTO;
import com.epam.redkin.model.service.RouteMappingService;
import com.epam.redkin.web.controller.command.Command;
import com.epam.redkin.web.listener.AppContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static com.epam.redkin.web.controller.Path.PAGE_ADMIN_ROUTE_DETAIL;

public class RouteMappingCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(RouteMappingCommand.class);
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("started");
        RouteMappingService routeMappingService = AppContext.getInstance().getRouteMappingService();
        String routeId = request.getParameter("routs_id");
        String stationId = request.getParameter("station_id");
        List<MappingInfoDTO> AllRoutToStationMappingListById = routeMappingService
                .getAllRoutToStationMappingListById(Integer.parseInt(routeId));
        request.setAttribute("station_id", stationId);
        request.setAttribute("routs_id", routeId);
        request.setAttribute("rout_m_list", AllRoutToStationMappingListById);
        LOGGER.info("done");
        return PAGE_ADMIN_ROUTE_DETAIL;
    }
}
