package com.epam.redkin.web.controller;


import com.epam.redkin.model.entity.*;
import com.epam.redkin.model.service.UserService;
import com.epam.redkin.model.service.*;
import com.epam.redkin.util.constants.AppContextConstant;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

@WebServlet("/administrator_info_user")
public class AdministratorInfoUserController extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(AdministratorInfoUserController.class);
    private UserService userService;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int page = 1;
        int recordsPerPage = 5;
        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }
        int noOfRecords = userService.getUserListSize();
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
        List<User> userInfoList = userService.getUserListByCurrentRecordAndRecordsPerPage(
                (page - 1) * recordsPerPage,
                recordsPerPage * page);
        request.setAttribute("recordsPerPage", recordsPerPage);
        request.setAttribute("noOfPages", noOfPages);
        request.setAttribute("currentPage", page);
        request.setAttribute("user_list", userInfoList);
        request.getRequestDispatcher("WEB-INF/jsp/administratorInfoUser.jsp").forward(request, response);
    }

    @Override
    public void init(ServletConfig config) {
        userService = (UserService) config.getServletContext().getAttribute(AppContextConstant.USER_SERVICE);
        LOGGER.trace("administrator_info_user Servlet init");
    }
}
