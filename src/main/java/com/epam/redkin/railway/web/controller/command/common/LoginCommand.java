package com.epam.redkin.railway.web.controller.command.common;

import com.epam.redkin.railway.model.entity.User;
import com.epam.redkin.railway.model.service.UserService;
import com.epam.redkin.railway.web.controller.Path;
import com.epam.redkin.railway.web.controller.command.Command;
import com.epam.redkin.railway.appcontext.AppContext;
import com.epam.redkin.railway.web.controller.command.Router;
import com.epam.redkin.railway.web.controller.command.SupportedLocaleStorage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

import static com.epam.redkin.railway.util.constants.AppContextConstant.*;

public class LoginCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginCommand.class);

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("started");
        UserService userService = AppContext.getInstance().getUserService();
        HttpSession session = request.getSession();
        String locale = SupportedLocaleStorage.getLocaleFromLanguage(Locale.getDefault().getLanguage()).getLanguage();
        String login = request.getParameter(USER_LOGIN);
        String password = request.getParameter(PASSWORD);
        Router currentRouter = new Router();
        currentRouter.setPagePath(Path.PAGE_LOGIN);
        currentRouter.setRouteType(Router.RouteType.FORWARD);

        if (login == null || password == null || login.isEmpty() || password.isEmpty()) {
            request.setAttribute(ERROR_MESSAGE, "Login or password can't be empty");
            LOGGER.info("Login or password is empty");
            return currentRouter;
        }
        User user = userService.isValidUser(login, password);
        LOGGER.info("User successfully extracted");
        currentRouter.setPagePath(Path.COMMAND_HOME);
        currentRouter.setRouteType(Router.RouteType.REDIRECT);
        session.setAttribute(SESSION_USER, user);
        session.setAttribute(CURRENT_DATE, LocalDate.now());
        session.setAttribute(LOCALE, locale);
        LOGGER.info("done");
        return currentRouter;
    }

    public String forward(String to) {
        return String.format("forward:%s", to);
    }
    public String redirect(String to) {
        return String.format("redirect:%s", to);
    }
}