package com.epam.redkin.web.controller.command.common;

import com.epam.redkin.model.entity.User;
import com.epam.redkin.web.controller.command.Command;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import static com.epam.redkin.web.controller.Path.*;

public class I18NCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(I18NCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("started");
        HttpSession session = request.getSession();

        if (request.getParameter(LANGUAGE) != null) {
            LOGGER.info("Set language: " + request.getParameter(LANGUAGE));
            session.setAttribute(LOCALE, request.getParameter(LANGUAGE));
        } else {
            LOGGER.info("Set default language: " + LOCALE_UA);
            session.setAttribute(LOCALE, LOCALE_UA);
        }
        User user = (User) session.getAttribute(SESSION_USER);
        String page = getRedirectPage(request);
        LOGGER.info("done");
        return user == null ? PAGE_LOGIN : page;
    }


    private String getRedirectPage(HttpServletRequest request) {
        String redirectPage = request.getParameter(LANGUAGE_PRE_SWITCH_PAGE_PARAMETERS);
        if (!StringUtils.isBlank(redirectPage)) {
            return CONTROLLER_NAME + "?" + redirectPage;
        }
        return request.getParameter(LANGUAGE_PRE_SWITCH_PAGE_ABSOLUTE_URL);
    }
}
