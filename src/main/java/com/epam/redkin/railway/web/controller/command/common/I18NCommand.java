package com.epam.redkin.railway.web.controller.command.common;

import com.epam.redkin.railway.util.constants.AppContextConstant;
import com.epam.redkin.railway.web.controller.Path;
import com.epam.redkin.railway.web.controller.command.Command;
import com.epam.redkin.railway.web.controller.command.Router;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;
import java.util.Objects;


public class I18NCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(I18NCommand.class);

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("started");
        Router router = new Router();

        HttpSession session = request.getSession();
        String currentLocale = request.getParameter(AppContextConstant.LANGUAGE);

        if (request.getParameter(AppContextConstant.LANGUAGE) != null) {
            LOGGER.info("Current locale: " + currentLocale);
            session.setAttribute(AppContextConstant.LOCALE, currentLocale);
            Locale.setDefault(new Locale(currentLocale));
        } else {
            LOGGER.info("Set default language: " + AppContextConstant.LOCALE_EN);
            session.setAttribute(AppContextConstant.LOCALE, AppContextConstant.LOCALE_EN);
            Locale.setDefault(new Locale(AppContextConstant.LOCALE_EN));
        }
        String page = getRedirectPage(request);
        router.setRouteType(Router.RouteType.FORWARD);
        router.setPagePath(Objects.requireNonNullElse(page, Path.PAGE_LOGIN));
        LOGGER.info("done");
        return router;
    }


    private String getRedirectPage(HttpServletRequest request) {
        String redirectPage = request.getParameter(AppContextConstant.LANGUAGE_PRE_SWITCH_PAGE_PARAMETERS);
        if (!StringUtils.isBlank(redirectPage)) {
            return AppContextConstant.CONTROLLER_NAME + "?" + redirectPage;
        }
        return request.getParameter(AppContextConstant.LANGUAGE_PRE_SWITCH_PAGE_ABSOLUTE_URL);
    }
}
