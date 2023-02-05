package com.epam.redkin.railway.web.controller;

public class Path {
    // pages
    public static final String PAGE_HOME = "WEB-INF/jsp/common/homePage.jsp";
    public static final String PAGE_LOGIN = "/login.jsp";
    public static final String PAGE_SEARCH_ROUTES = "WEB-INF/jsp/common/searchRoutForOrder.jsp";
    public static final String PAGE_ORDERS = "WEB-INF/jsp/user/userAccount.jsp";
    public static final String PAGE_ROUTE_DETAIL = "WEB-INF/jsp/common/detailRout.jsp";
    public static final String PAGE_SELECT_STATION_AND_CARRIAGE_TYPE = "WEB-INF/jsp/common/selectStationAndCarriageType.jsp";
    public static final String PAGE_SELECT_CARRIAGE_AND_COUNT_SEATS = "WEB-INF/jsp/common/selectCarriageAndCountSeats.jsp";
    public static final String PAGE_SELECT_SEATS_NUMBER = "WEB-INF/jsp/common/selectSeats.jsp";
    public static final String PAGE_CONFIRM_ORDER = "WEB-INF/jsp/common/confirmOrder.jsp";
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
    public static final String PAGE_ERROR_PAGE = "errorPage.jsp";
    public static final String PAGE_PROFILE = "WEB-INF/jsp/user/profile.jsp";



    // admin commands
    public static final String COMMAND_HOME = "controller?action=home";
    public static final String COMMAND_INFO_ROUTE = "controller?action=routes";
    public static final String COMMAND_ROUTE_MAPPING = "controller?action=route_mapping";
    public static final String COMMAND_SEARCH_ROUTES = "controller?action=search_routes";
    public static final String COMMAND_INFO_STATIONS = "controller?action=stations";
    public static final String COMMAND_INFO_USERS = "controller?action=users";
    public static final String COMMAND_INFO_ORDERS = "controller?action=admin_orders";
    public static final String COMMAND_INFO_CARRIAGES = "controller?action=carriages";
    public static final String COMMAND_INFO_TRAINS = "controller?action=trains";

    // user commands
    public static final String COMMAND_ORDERS = "controller?action=orders";
    public static final String COMMAND_LOGIN_BY_TOKEN_LINK = "controller?action=login_by_token_link";
    public static final String COMMAND_POST_REGISTRATION_ACCOUNT_APPROVAL = "?action=postRegistrationAccountApproval";
}
