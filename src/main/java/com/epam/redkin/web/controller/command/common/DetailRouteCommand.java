package com.epam.redkin.web.controller.command.common;

import com.epam.redkin.model.dto.MappingInfoDTO;
import com.epam.redkin.model.exception.IncorrectDataException;
import com.epam.redkin.service.RouteMappingService;
import com.epam.redkin.util.constants.AppContextConstant;
import com.epam.redkin.web.controller.command.Command;
import com.epam.redkin.web.listener.AppContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;

import static com.epam.redkin.web.controller.Path.LOCALE;
import static com.epam.redkin.web.controller.Path.PAGE_ROUTE_DETAIL;

public class DetailRouteCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(DetailRouteCommand.class);
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.debug("DetailRouteCommand started");
        RouteMappingService routeMappingService = AppContext.getInstance().getRouteMappingService();
        String routsId = request.getParameter("routs_id");
        String userId = request.getParameter("user_id");
        String departureStation = request.getParameter("departure_station");
        String arrivalStation = request.getParameter("arrival_station");
        LocalDateTime departureDate;
        try {
            departureDate = LocalDateTime.parse(request.getParameter("departure_date"));
        } catch (DateTimeParseException e) {
            LOGGER.error("Incorrect data entered");
            throw new IncorrectDataException("Incorrect data entered", e);
        }
        List<MappingInfoDTO> allRouteToStationMappingListById = routeMappingService
                .getAllRoutToStationMappingListById(Integer.parseInt(routsId));
        request.setAttribute("departure_station", departureStation);
        request.setAttribute("arrival_station", arrivalStation);
        request.setAttribute("departure_date", departureDate);
        request.setAttribute("user_id", userId);
        request.setAttribute("routs_id", routsId);
        request.setAttribute("rout_m_list", allRouteToStationMappingListById);
        HttpSession session = request.getSession();
        request.setAttribute("lang", session.getAttribute(LOCALE));
        return PAGE_ROUTE_DETAIL;
    }
}
