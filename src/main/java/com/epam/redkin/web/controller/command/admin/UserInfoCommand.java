package com.epam.redkin.web.controller.command.admin;

import com.epam.redkin.model.entity.User;
import com.epam.redkin.model.service.UserService;
import com.epam.redkin.web.controller.command.Command;
import com.epam.redkin.web.listener.AppContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static com.epam.redkin.web.controller.Path.PAGE_ADMIN_INFO_USER;

public class UserInfoCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserInfoCommand.class);
    private static final int RECORDS_PER_PAGE = 5;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("started");
        UserService userService = AppContext.getInstance().getUserService();
        int page = 1;
        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }
        int noOfRecords = userService.getUserListSize();
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / RECORDS_PER_PAGE);
        List<User> userInfoList = userService.getUserListByCurrentRecordAndRecordsPerPage(
                (page - 1) * RECORDS_PER_PAGE,
                RECORDS_PER_PAGE * page);
        request.setAttribute("recordsPerPage", RECORDS_PER_PAGE);
        request.setAttribute("noOfPages", noOfPages);
        request.setAttribute("currentPage", page);
        request.setAttribute("user_list", userInfoList);
        LOGGER.info("done");
        return PAGE_ADMIN_INFO_USER;
    }
}
