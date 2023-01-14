package com.epam.redkin.web.controller.command.admin;

import com.epam.redkin.model.service.UserService;
import com.epam.redkin.web.controller.command.Command;
import com.epam.redkin.web.listener.AppContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.epam.redkin.web.controller.Path.COMMAND_INFO_USERS;

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
        return COMMAND_INFO_USERS;
    }
}
