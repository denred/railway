package com.epam.redkin.railway.web.controller.command.common;

import com.epam.redkin.railway.appcontext.AppContext;
import com.epam.redkin.railway.model.dto.RouteInfoDTO;
import com.epam.redkin.railway.model.entity.Carriage;
import com.epam.redkin.railway.model.service.CarriageService;
import com.epam.redkin.railway.model.service.RouteService;
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
import java.util.stream.Collectors;

import static com.epam.redkin.railway.util.constants.AppContextConstant.*;

public class SelectCarriageAndSeatsPage implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(SelectCarriageAndSeatsPage.class);

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("started");
        Router router = new Router();
        router.setRouteType(Router.RouteType.FORWARD);
        router.setPagePath(Path.PAGE_SELECT_CARRIAGE_AND_COUNT_SEATS);
        LOGGER.info("done");
        return router;
    }
}
