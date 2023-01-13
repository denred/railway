package com.epam.redkin.web.controller.command.common;

import com.epam.redkin.service.LogoutService;
import com.epam.redkin.web.controller.command.Command;
import com.epam.redkin.web.listener.AppContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.epam.redkin.web.controller.Path.PAGE_LOGIN;

public class LogoutCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(LogoutCommand.class);
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("started");
        LogoutService logoutService = AppContext.getInstance().getLogoutService();
        logoutService.logout(request);
        LOGGER.info("done");
        return PAGE_LOGIN;
    }
}
