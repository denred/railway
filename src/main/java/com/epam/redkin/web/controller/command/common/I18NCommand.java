package com.epam.redkin.web.controller.command.common;

import com.epam.redkin.model.entity.User;
import com.epam.redkin.web.controller.Path;
import com.epam.redkin.web.controller.command.Command;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.jsp.jstl.core.Config;

public class I18NCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();

        String fmtLocale = "javax.servlet.jsp.jstl.fmt.locale";
        String defaultLocale = "defaultLocale";

        if (request.getParameter("ua") != null) {
            Config.set(session, fmtLocale, Path.LOCALE_NAME_UA);
            session.setAttribute(defaultLocale, "ua");

        } else {
            Config.set(session, fmtLocale, "en");
            session.setAttribute(defaultLocale, Path.LOCALE_NAME_EN);
        }

        User user = (User) session.getAttribute("user");
        return (user.getRoleId() == 1) ? "controller?action=users" : "controller?action=account";
    }
}
