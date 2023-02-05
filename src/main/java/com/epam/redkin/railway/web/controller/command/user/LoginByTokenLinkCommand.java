package com.epam.redkin.railway.web.controller.command.user;

import com.epam.redkin.railway.model.entity.User;
import com.epam.redkin.railway.model.exception.ServiceException;
import com.epam.redkin.railway.model.service.UserService;
import com.epam.redkin.railway.util.constants.AppContextConstant;
import com.epam.redkin.railway.web.controller.Path;
import com.epam.redkin.railway.web.controller.command.Command;
import com.epam.redkin.railway.appcontext.AppContext;
import com.epam.redkin.railway.web.controller.command.Router;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginByTokenLinkCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginByTokenLinkCommand.class);

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("started");
        Router router = new Router();
        router.setRouteType(Router.RouteType.FORWARD);
        router.setPagePath(Path.PAGE_HOME);
        UserService userService = AppContext.getInstance().getUserService();
        String token = request.getParameter(AppContextConstant.COOKIE_REMEMBER_USER_TOKEN);
        if (StringUtils.isNoneBlank(token)) {
            try {
                User user = userService.logInByToken(token);
                LOGGER.debug(user.toString());
                request.getSession().setAttribute(AppContextConstant.EMAIL, user.getEmail());
                request.getSession().setAttribute(AppContextConstant.USER_ROLE, user.getRole().name());
                request.getSession().setAttribute(AppContextConstant.USER_ID, user.getUserId());
                request.getSession().setAttribute(AppContextConstant.SESSION_USER, user);
                userService.deleteRememberUserToken(user.getUserId());
            } catch (ServiceException e) {
                LOGGER.error("Incorrect data entered", e);
                throw new ServiceException("Incorrect data entered");
            }
        }
        LOGGER.info("done");
        return router;
    }
}
