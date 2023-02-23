package com.epam.redkin.railway.web.controller.command.admin;

import com.epam.redkin.railway.model.entity.User;
import com.epam.redkin.railway.model.service.PaginationService;
import com.epam.redkin.railway.model.service.SearchService;
import com.epam.redkin.railway.model.service.UserService;
import com.epam.redkin.railway.web.controller.Path;
import com.epam.redkin.railway.web.controller.command.Command;
import com.epam.redkin.railway.appcontext.AppContext;
import com.epam.redkin.railway.web.controller.command.Router;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.epam.redkin.railway.util.constants.AppContextConstant.*;

public class UserInfoCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserInfoCommand.class);
    private static final int RECORDS_PER_PAGE = 5;
    private static final int FIRST_VISIBLE_PAGE_LINK = 5;
    private final AppContext appContext;

    public UserInfoCommand(AppContext appContext) {
        this.appContext = appContext;
    }

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("started");
        UserService userService = appContext.getUserService();
        PaginationService paginationService = appContext.getPaginationService();
        SearchService searchService = appContext.getSearchService();

        HttpSession session = request.getSession();
        Map<String, String> search = new HashMap<>();

        String email = searchService.getParameter(request, EMAIL_FIELD);
        String firstName = searchService.getParameter(request, FIRST_NAME_FIELD);
        String lastName = searchService.getParameter(request, LAST_NAME_FIELD);
        String phone = searchService.getParameter(request, PHONE_NUMBER_FIELD);
        String birthDate = request.getParameter(BIRTH_DATE_FIELD);
        if (searchService.isFieldsInvalid(email, firstName, lastName, phone, birthDate)) {
            return Router.redirect(Path.COMMAND_GET_PAGE_INFO_USERS);
        }

        searchService.addSearch(search, EMAIL, email);
        searchService.addSearch(search, FIRST_NAME, firstName);
        searchService.addSearch(search, LAST_NAME, lastName);
        searchService.addSearch(search, PHONE_NUMBER, phone);
        searchService.addSearch(search, BIRTH_DATE, birthDate);

        int page = paginationService.getPage(request);
        int records = userService.getUserCount(search);
        List<User> userInfoList = userService.getUserList((page - 1) * RECORDS_PER_PAGE, RECORDS_PER_PAGE, search);
        paginationService.setPaginationParameter(request, page, records, RECORDS_PER_PAGE, FIRST_VISIBLE_PAGE_LINK);

        session.setAttribute(LIST_OF_USERS, userInfoList);
        LOGGER.info("done");
        return Router.redirect(Path.COMMAND_GET_PAGE_INFO_USERS);
    }
}
