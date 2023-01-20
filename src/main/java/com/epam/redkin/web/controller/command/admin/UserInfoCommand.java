package com.epam.redkin.web.controller.command.admin;

import com.epam.redkin.model.entity.User;
import com.epam.redkin.model.service.UserService;
import com.epam.redkin.web.controller.command.Command;
import com.epam.redkin.web.listener.AppContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.epam.redkin.util.constants.AppContextConstant.*;
import static com.epam.redkin.web.controller.Path.PAGE_ADMIN_INFO_USER;

public class UserInfoCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserInfoCommand.class);
    private static final int RECORDS_PER_PAGE = 5;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("started");
        UserService userService = AppContext.getInstance().getUserService();
        Map<String, String> search = new HashMap<>();
        int page = 1;
        String email = request.getParameter(EMAIL);
        String firstName = request.getParameter(FIRST_NAME);
        String lastName = request.getParameter(LAST_NAME);
        String phone = request.getParameter(PHONE_NUMBER);
        String birthDate = request.getParameter(BIRTH_DATE);
        String role = request.getParameter(ROLE);
        String blocked = request.getParameter(BLOCKED);
        addSearch(search, EMAIL, email);
        addSearch(search, FIRST_NAME, firstName);
        addSearch(search, LAST_NAME, lastName);
        addSearch(search, PHONE_NUMBER, phone);
        addSearch(search, BIRTH_DATE, birthDate);
        addSearch(search, ROLE, role);
        addSearch(search, BLOCKED, blocked);
        if (request.getParameter(PAGE) != null) {
            page = Integer.parseInt(request.getParameter(PAGE));
        }
        int recordsCount = userService.getUserCount(search);
        int pagesCount = (int) Math.ceil(recordsCount * 1.0 / RECORDS_PER_PAGE);
        List<User> userInfoList = userService.getUserList((page - 1) * RECORDS_PER_PAGE, RECORDS_PER_PAGE, search);
        request.setAttribute(PAGE_RECORDS, RECORDS_PER_PAGE);
        request.setAttribute(PAGE_COUNT, pagesCount);
        request.setAttribute(CURRENT_PAGE, page);
        request.setAttribute(LIST_OF_USERS, userInfoList);
        request.setAttribute(EMAIL, email);
        request.setAttribute(FIRST_NAME, firstName);
        request.setAttribute(LAST_NAME, lastName);
        request.setAttribute(PHONE_NUMBER, phone);
        request.setAttribute(BIRTH_DATE, birthDate);
        request.setAttribute(ROLE, role);
        request.setAttribute(BLOCKED, blocked);
        LOGGER.info("done");
        return PAGE_ADMIN_INFO_USER;
    }

    private void addSearch(Map<String, String> search, String key, String value) {
        if (StringUtils.isNoneBlank(value)) {
            search.put(key, value);
        }
    }
}
