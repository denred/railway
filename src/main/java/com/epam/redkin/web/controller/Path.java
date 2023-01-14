package com.epam.redkin.web.controller;

public class Path {
    // pages
    public static final String PAGE_HOME = "WEB-INF/jsp/homePage.jsp";
    public static final String PAGE_LOGIN = "/login.jsp";
    public static final String PAGE_SEARCH_ROUTES = "WEB-INF/jsp/searchRoutForOrder.jsp";
    public static final String PAGE_ORDERS = "WEB-INF/jsp/user/userAccount.jsp";
    public static final String PAGE_ROUTE_DETAIL = "WEB-INF/jsp/detailRout.jsp";
    public static final String PAGE_SELECT_STATION_AND_CARRIAGE_TYPE = "WEB-INF/jsp/selectStationAndCarriageType.jsp";
    public static final String PAGE_SELECT_CARRIAGE_AND_COUNT_SEATS = "WEB-INF/jsp/selectCarriageAndCountSeats.jsp";
    public static final String PAGE_SELECT_SEATS_NUMBER = "WEB-INF/jsp/selectSeats.jsp";
    public static final String PAGE_CONFIRM_ORDER = "WEB-INF/jsp/confirmOrder.jsp";
    public static final String PAGE_INFO_ROUTE = "WEB-INF/jsp/admin/routeInfo.jsp";
    public static final String PAGE_ADD_ROUTE = "WEB-INF/jsp/admin/routeAdd.jsp";
    public static final String PAGE_EDIT_ROUTE = "WEB-INF/jsp/admin/routeEdit.jsp";
    public static final String PAGE_ADMIN_ROUTE_DETAIL = "WEB-INF/jsp/admin/routeMapping.jsp";
    public static final String PAGE_ADMIN_SET_STATION_IN_ROUTE = "WEB-INF/jsp/admin/routeMappingSetStation.jsp";
    public static final String PAGE_ERROR_PAGE = "/WEB-INF/errorPage.jsp";
    public static final String PAGE_MAIN = "/WEB-INF/jsp/admin/main.jsp";
    public static final String PAGE_PROFILE = "/WEB-INF/jsp/admin/profile.jsp";
    public static final String PAGE_ACCOUNT = "/WEB-INF/jsp/client/account.jsp";
    public static final String PAGE_USER_PROFILE = "/WEB-INF/jsp/client/personaldata.jsp";
    public static final String PAGE_USER_LIST = "/WEB-INF/jsp/admin/abonents.jsp";
    public static final String PAGE_SERVICES = "/WEB-INF/jsp/admin/services.jsp";
    public static final String PAGE_TRANSACTIONS = "/WEB-INF/jsp/client/transactions.jsp";

    // common commands
    public static final String COMMAND_LOGIN = "controller?action=login";
    public static final String COMMAND_LOGOUT = "controller?action=logout";
    public static final String COMMAND_PDF_BUILDER = "controller?action=pdf_builder";
    public static final String COMMAND_NO_COMMAND = "controller?action=no_command";
    public static final String COMMAND_I18N = "controller?action=i18n";
    public static final String COMMAND_REDIRECT = "redirect";

    // admin commands
    public static final String COMMAND_MAIN = "controller?action=main";
    public static final String COMMAND_SHOW_USERS = "controller?action=users";
    public static final String COMMAND_HOME = "controller?action=home";
    public static final String COMMAND_INFO_ROUTE = "controller?action=routes";
    public static final String COMMAND_ROUTE_MAPPING = "controller?action=route_mapping";
    public static final String COMMAND_SHOW_SERVICES = "controller?action=services";
    public static final String COMMAND_REGISTRATION = "controller?action=registration";
    public static final String COMMAND_EDIT_CLIENT = "controller?action=edit_client";
    public static final String COMMAND_PROFILE = "controller?action=profile";
    public static final String COMMAND_ADD_TARIFF = "controller?action=add_tariff";
    public static final String COMMAND_EDIT_TARIFF = "controller?action=edit_tariff";
    public static final String COMMAND_REMOVE_TARIFF = "controller?action=remove_tariff";

    // client commands
    public static final String COMMAND_ORDERS = "controller?action=orders";
    public static final String COMMAND_ACCOUNT = "controller?action=account";
    public static final String COMMAND_PERSONAL_DATA = "controller?action=personal_data";
    public static final String COMMAND_SAVE_PROFILE = "controller?action=save_profile";
    public static final String COMMAND_TRANSACTIONS = "controller?action=transactions";

    // i18n
    public static final String LOCALE_UA = "ua";
    public static final String LOCALE_EN = "en";
    public static final String LOCALE = "locale";
    public static final String LANGUAGE = "lang";

    //User
    public static final String USER_ID = "user_id";
    public static final String SESSION_USER = "user";

    //Order
    public static final String ORDER_LIST = "order_list";
    public static final String TRAVEL_TIME = "travel_time";

    //Route
    public static final String ROUTE_ID = "routs_id";

    //Train
    public static final String TRAIN_ID = "train_id";

    //Station
    public static final String ARRIVAL_STATION_ID = "arrival_station_id";
    public static final String DEPARTURE_STATION_ID = "departure_station_id";


    //Carriage
    public static final String CARRIAGE_ID = "car_id";
    public static final String CARRIAGE_TYPE = "car_type";

    //Seat
    public static final String SEATS_ID = "seats_id";
    public static final String COUNT_SEATS = "count_of_seats";

    //RouteDTO
    public static final String ROUTE_DTO_LIST = "routeDto_list";



    //Util
    public static final String PAGE_RECORDS = "recordsPerPage";
    public static final String PAGE_COUNT = "noOfPages";
    public static final String CURRENT_PAGE = "currentPage";
    public static final String SUM = "sum";
    public static final String PAGE = "page";


}
