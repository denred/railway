package com.epam.redkin.railway.web.controller.command.common;

import com.epam.redkin.railway.model.dto.MappingInfoDTO;
import com.epam.redkin.railway.model.service.PaginationService;
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
    private static final int RECORDS_PER_PAGE = 5;
    private static final int FIRST_VISIBLE_PAGE_LINK = 5;

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.debug("started");
        RouteMappingService routeMappingService = AppContext.getInstance().getRouteMappingService();
        PaginationService paginationService = AppContext.getInstance().getPaginationService();
        HttpSession session = request.getSession();
        Router router = new Router();
        router.setRouteType(Router.RouteType.REDIRECT);
        router.setPagePath(Path.COMMAND_ROUTE_DETAIL);

        int page = request.getParameter(PAGE) != null ? Integer.parseInt(request.getParameter(PAGE)) : 1;

        String routeId = request.getParameter(ROUTE_ID);
        if (StringUtils.isBlank(routeId)) {
            routeId = (String) session.getAttribute(ROUTE_ID);
        }

        List<MappingInfoDTO> mappingInfoDTOList = routeMappingService
                .getMappingInfoDtoListByRouteIdAndPagination(Integer.parseInt(routeId), (page - 1) * RECORDS_PER_PAGE,
                        RECORDS_PER_PAGE);
        int records = routeMappingService.getRouteStationsCount(Integer.parseInt(routeId));
        paginationService.setPaginationParameter(request, page, records, RECORDS_PER_PAGE, FIRST_VISIBLE_PAGE_LINK);

        session.setAttribute(ROUTE_MAPPING_LIST, mappingInfoDTOList);
        session.setAttribute(ROUTE_ID, routeId);
        session.setAttribute(LAST_STATION, records);

        LOGGER.info("done");
        return router;
    }
}
