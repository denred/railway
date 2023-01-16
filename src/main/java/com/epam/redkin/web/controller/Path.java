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
    public static final String PAGE_ADMIN_INFO_STATION = "WEB-INF/jsp/admin/stationInfo.jsp";
    public static final String PAGE_ADMIN_SET_STATION = "WEB-INF/jsp/admin/stationSet.jsp";
    public static final String PAGE_ADMIN_INFO_USER = "WEB-INF/jsp/admin/userInfo.jsp";
    public static final String PAGE_ADMIN_INFO_CARRIAGE = "WEB-INF/jsp/admin/carriageInfo.jsp";
    public static final String PAGE_ADMIN_INFO_TRAIN = "WEB-INF/jsp/admin/trainInfo.jsp";
    public static final String PAGE_ADMIN_INFO_ORDER = "WEB-INF/jsp/admin/orderInfo.jsp";
    public static final String PAGE_ADMIN_SET_ORDER_STATUS = "WEB-INF/jsp/admin/orderStatus.jsp";
    public static final String PAGE_ADMIN_SET_CARRIAGE = "WEB-INF/jsp/admin/carriageSet.jsp";
    public static final String PAGE_ADMIN_SET_TRAIN = "WEB-INF/jsp/admin/trainSet.jsp";
    public static final String PAGE_FORGET_PASSWORD = "WEB-INF/jsp/user/forgetPassword.jsp";
    public static final String PAGE_REGISTRATION = "registration.jsp";


    // common commands
    public static final String COMMAND = "action";
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
    public static final String COMMAND_INFO_STATIONS = "controller?action=stations";
    public static final String COMMAND_INFO_USERS = "controller?action=users";
    public static final String COMMAND_INFO_ORDERS = "controller?action=admin_orders";
    public static final String COMMAND_INFO_CARRIAGES = "controller?action=carriages";
    public static final String COMMAND_INFO_TRAINS = "controller?action=trains";

    // user commands
    public static final String COMMAND_ORDERS = "controller?action=orders";
    public static final String COMMAND_FORGET_PASSWORD_LOGIN = "controller?action=logInByForgetPasswordLink";
    public static final String POST_REGISTRATION_ACCOUNT_APPROVAL = "controller?action=postRegistrationAccountApproval";
    public static final String COMMAND_ACCOUNT = "controller?action=account";
    public static final String COMMAND_PERSONAL_DATA = "controller?action=personal_data";
    public static final String COMMAND_SAVE_PROFILE = "controller?action=save_profile";
    public static final String COMMAND_TRANSACTIONS = "controller?action=transactions";

    // i18n
    public static final String LOCALE_UA = "ua";
    public static final String LOCALE_EN = "en";
    public static final String LOCALE = "locale";
    public static final String LANGUAGE = "lang";
    public static final String LANGUAGE_PRE_SWITCH_PAGE_PARAMETERS = "currentParameters";
    public static final String LANGUAGE_PRE_SWITCH_PAGE_ABSOLUTE_URL = "currentPageAbsoluteURL";


    //User
    public static final String USER_ID = "user_id";
    public static final String SESSION_USER = "user";
    public static final String CONTROLLER_NAME = "controller";
    public static final String COOKIE_REMEMBER_USER_TOKEN_DIVIDER = ":";
    public static final String COOKIE_REMEMBER_USER_TOKEN = "rememberToken";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
    public static final String USER_LOGIN = "login";
    public static final String USER_ROLE = "user";
    public static final String ERROR_MESSAGE = "Login or password can't be empty";


    public static final String FIRST_NAME = "first_name";
    public static final String LAST_NAME = "last_name";
    public static final String PHONE_NUMBER = "phone";
    public static final String BIRTH_DATE = "birth_date";


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
    public static final String DEPARTURE_STATION = "departure_station";
    public static final String ARRIVAL_STATION = "arrival_station";
    public static final String DEPARTURE_DATE = "departure_date";
    public static final String CURRENT_DATE_TIME = "dateTime";



    //Carriage
    public static final String CARRIAGE_ID = "car_id";
    public static final String CARRIAGE_TYPE = "car_type";
    public static final String CARRIAGE_TYPE_LIST = "carTypeList";

    //Seat
    public static final String SEATS_ID = "seats_id";
    public static final String COUNT_SEATS = "count_of_seats";

    //RouteDTO
    public static final String ROUTE_DTO_LIST = "routeDto_list";
    public static final String ROUTE_ORDER_DTO_LIST = "rout_order_dto_list";



    //Util
    public static final String PAGE_RECORDS = "recordsPerPage";
    public static final String PAGE_COUNT = "noOfPages";
    public static final String CURRENT_PAGE = "currentPage";
    public static final String SUM = "sum";
    public static final String PAGE = "page";


}
