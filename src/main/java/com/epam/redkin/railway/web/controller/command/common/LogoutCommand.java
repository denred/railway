package com.epam.redkin.railway.web.controller.command.common;

import com.epam.redkin.railway.model.service.LogoutService;
import com.epam.redkin.railway.web.controller.Path;
import com.epam.redkin.railway.web.controller.command.Command;
import com.epam.redkin.railway.appcontext.AppContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogoutCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(LogoutCommand.class);
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("started");
        LogoutService logoutService = AppContext.getInstance().getLogoutService();
        logoutService.logout(request);
        LOGGER.info("done");
        return Path.PAGE_LOGIN;
    }
}
