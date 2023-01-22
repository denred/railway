package com.epam.redkin.railway.web.controller.command.common;

import com.epam.redkin.railway.util.constants.AppContextConstant;
import com.epam.redkin.railway.web.controller.Path;
import com.epam.redkin.railway.web.controller.command.Command;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class I18NCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(I18NCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("started");
        HttpSession session = request.getSession();

        if (request.getParameter(AppContextConstant.LANGUAGE) != null) {
            LOGGER.info("Set language: " + request.getParameter(AppContextConstant.LANGUAGE));
            session.setAttribute(AppContextConstant.LOCALE, request.getParameter(AppContextConstant.LANGUAGE));
        } else {
            LOGGER.info("Set default language: " + AppContextConstant.LOCALE_UA);
            session.setAttribute(AppContextConstant.LOCALE, AppContextConstant.LOCALE_UA);
        }
        String page = getRedirectPage(request);
        LOGGER.info("done");
        return page == null ? Path.PAGE_LOGIN : page;
    }


    private String getRedirectPage(HttpServletRequest request) {
        String redirectPage = request.getParameter(AppContextConstant.LANGUAGE_PRE_SWITCH_PAGE_PARAMETERS);
        if (!StringUtils.isBlank(redirectPage)) {
            return AppContextConstant.CONTROLLER_NAME + "?" + redirectPage;
        }
        return request.getParameter(AppContextConstant.LANGUAGE_PRE_SWITCH_PAGE_ABSOLUTE_URL);
    }
}
