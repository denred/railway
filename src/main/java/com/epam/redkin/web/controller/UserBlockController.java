package com.epam.redkin.web.controller;


import com.epam.redkin.model.service.UserService;
import com.epam.redkin.util.constants.AppContextConstant;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@WebServlet("/user_block")
public class UserBlockController extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserBlockController.class);

    private UserService userService;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userId = request.getParameter("user_id");
        boolean blockStatus = Boolean.parseBoolean(request.getParameter("block_status"));
        userService.updateBlocked(Integer.parseInt(userId), blockStatus);
        response.sendRedirect("administrator_info_user");
    }

    @Override
    public void init(ServletConfig config) {
        userService = (UserService) config.getServletContext().getAttribute(AppContextConstant.USER_SERVICE);
        LOGGER.trace("user_block Servlet init");
    }
}
