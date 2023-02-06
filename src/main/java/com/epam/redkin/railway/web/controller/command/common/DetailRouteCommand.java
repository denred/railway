package com.epam.redkin.railway.web.controller.command.common;

import com.epam.redkin.railway.model.dto.MappingInfoDTO;
import com.epam.redkin.railway.model.service.RouteMappingService;
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

import java.util.List;

import static com.epam.redkin.railway.util.constants.AppContextConstant.*;

public class DetailRouteCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(DetailRouteCommand.class);

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.debug("started");
        Router router = new Router();
        HttpSession session = request.getSession();
        router.setRouteType(Router.RouteType.FORWARD);
        router.setPagePath(Path.PAGE_ROUTE_DETAIL);
        RouteMappingService routeMappingService = AppContext.getInstance().getRouteMappingService();
        String routeId = request.getParameter(ROUTE_ID);

        if (StringUtils.isNoneBlank(routeId)) {
            List<MappingInfoDTO> allRouteToStationMappingListById = routeMappingService
                    .getMappingInfoDtoListByRouteId(Integer.parseInt(routeId));

            session.setAttribute(ROUTE_MAPPING_LIST, allRouteToStationMappingListById);
            router.setRouteType(Router.RouteType.REDIRECT);
            router.setPagePath(Path.COMMAND_ROUTE_DETAIL);
        }

        request.setAttribute(LANGUAGE, session.getAttribute(LOCALE));
        return router;
    }
}
