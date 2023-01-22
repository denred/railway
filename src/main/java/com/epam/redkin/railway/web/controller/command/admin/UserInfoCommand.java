package com.epam.redkin.railway.web.controller.command.admin;

import com.epam.redkin.railway.model.entity.User;
import com.epam.redkin.railway.model.service.UserService;
import com.epam.redkin.railway.util.constants.AppContextConstant;
import com.epam.redkin.railway.web.controller.Path;
import com.epam.redkin.railway.web.controller.command.Command;
import com.epam.redkin.railway.appcontext.AppContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserInfoCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserInfoCommand.class);
    private static final int RECORDS_PER_PAGE = 5;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("started");
        UserService userService = AppContext.getInstance().getUserService();
        Map<String, String> search = new HashMap<>();
        int page = 1;
        String email = request.getParameter(AppContextConstant.EMAIL);
        String firstName = request.getParameter(AppContextConstant.FIRST_NAME);
        String lastName = request.getParameter(AppContextConstant.LAST_NAME);
        String phone = request.getParameter(AppContextConstant.PHONE_NUMBER);
        String birthDate = request.getParameter(AppContextConstant.BIRTH_DATE);
        String role = request.getParameter(AppContextConstant.ROLE);
        String blocked = request.getParameter(AppContextConstant.BLOCKED);
        addSearch(search, AppContextConstant.EMAIL, email);
        addSearch(search, AppContextConstant.FIRST_NAME, firstName);
        addSearch(search, AppContextConstant.LAST_NAME, lastName);
        addSearch(search, AppContextConstant.PHONE_NUMBER, phone);
        addSearch(search, AppContextConstant.BIRTH_DATE, birthDate);
        addSearch(search, AppContextConstant.ROLE, role);
        addSearch(search, AppContextConstant.BLOCKED, blocked);
        if (request.getParameter(AppContextConstant.PAGE) != null) {
            page = Integer.parseInt(request.getParameter(AppContextConstant.PAGE));
        }
        int recordsCount = userService.getUserCount(search);
        int pagesCount = (int) Math.ceil(recordsCount * 1.0 / RECORDS_PER_PAGE);
        List<User> userInfoList = userService.getUserList((page - 1) * RECORDS_PER_PAGE, RECORDS_PER_PAGE, search);
        request.setAttribute(AppContextConstant.PAGE_RECORDS, RECORDS_PER_PAGE);
        request.setAttribute(AppContextConstant.PAGE_COUNT, pagesCount);
        request.setAttribute(AppContextConstant.CURRENT_PAGE, page);
        request.setAttribute(AppContextConstant.LIST_OF_USERS, userInfoList);
        request.setAttribute(AppContextConstant.EMAIL, email);
        request.setAttribute(AppContextConstant.FIRST_NAME, firstName);
        request.setAttribute(AppContextConstant.LAST_NAME, lastName);
        request.setAttribute(AppContextConstant.PHONE_NUMBER, phone);
        request.setAttribute(AppContextConstant.BIRTH_DATE, birthDate);
        request.setAttribute(AppContextConstant.ROLE, role);
        request.setAttribute(AppContextConstant.BLOCKED, blocked);
        LOGGER.info("done");
        return Path.PAGE_ADMIN_INFO_USER;
    }

    private void addSearch(Map<String, String> search, String key, String value) {
        if (StringUtils.isNoneBlank(value)) {
            search.put(key, value);
        }
    }
}
