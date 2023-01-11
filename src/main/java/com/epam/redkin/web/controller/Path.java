package com.epam.redkin.web.controller;

public class Path {
    // pages
    public static final String PAGE_INDEX = "/index.jsp";
    public static final String PAGE_LOGIN = "/login.jsp";
    public static final String PAGE_ERROR_PAGE = "/WEB-INF/jsp/error_page.jsp";
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
    public static final String COMMAND_SHOW_SERVICES = "controller?action=services";
    public static final String COMMAND_REGISTRATION = "controller?action=registration";
    public static final String COMMAND_EDIT_CLIENT = "controller?action=edit_client";
    public static final String COMMAND_PROFILE = "controller?action=profile";
    public static final String COMMAND_ADD_TARIFF = "controller?action=add_tariff";
    public static final String COMMAND_EDIT_TARIFF = "controller?action=edit_tariff";
    public static final String COMMAND_REMOVE_TARIFF = "controller?action=remove_tariff";

    // client commands
    public static final String COMMAND_ACCOUNT = "controller?action=account";
    public static final String COMMAND_PERSONAL_DATA = "controller?action=personal_data";
    public static final String COMMAND_SAVE_PROFILE = "controller?action=save_profile";
    public static final String COMMAND_TRANSACTIONS = "controller?action=transactions";

    // i18n
    public static final String LOCALE_UA = "ua";
    public static final String LOCALE_EN = "en";
    public static final String LOCALE = "locale";
    public static final String LANG = "lang";

}
