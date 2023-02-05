package com.epam.redkin.railway.web.controller.command.common;

import com.epam.redkin.railway.model.entity.User;
import com.epam.redkin.railway.util.constants.AppContextConstant;
import com.epam.redkin.railway.web.controller.Path;
import com.epam.redkin.railway.web.controller.command.Command;
import com.epam.redkin.railway.web.controller.command.Router;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import static com.epam.redkin.railway.util.constants.AppContextConstant.*;

public class HomeCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        Router router = new Router();
        User user = (User) session.getAttribute(AppContextConstant.SESSION_USER);
        String fromStation = request.getParameter(FROM_STATION);
        String toStation = request.getParameter(TO_STATION);

        request.setAttribute(FROM_STATION, fromStation);
        request.setAttribute(TO_STATION, toStation);
        request.setAttribute(ROLE, user.getRole());
        router.setRouteType(Router.RouteType.FORWARD);
        router.setPagePath(Path.PAGE_HOME);

        return router;
    }
}


