package com.epam.redkin.railway.web.controller.command.admin;

import com.epam.redkin.railway.model.service.UserService;
import com.epam.redkin.railway.web.controller.Path;
import com.epam.redkin.railway.web.controller.command.Command;
import com.epam.redkin.railway.appcontext.AppContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserBlockCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserBlockCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("started");
        UserService userService = AppContext.getInstance().getUserService();
        String userId = request.getParameter("user_id");
        String userStatus = request.getParameter("block_status");
        boolean blockStatus = Boolean.parseBoolean(userStatus);
        userService.updateBlocked(Integer.parseInt(userId), blockStatus);
        LOGGER.info("done");
        return Path.COMMAND_INFO_USERS;
    }
}
