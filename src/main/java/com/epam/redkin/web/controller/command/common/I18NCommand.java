package com.epam.redkin.web.controller.command.common;

import com.epam.redkin.model.entity.User;
import com.epam.redkin.web.controller.command.Command;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.Objects;

import static com.epam.redkin.web.controller.Path.*;

public class I18NCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();

        if (!Objects.isNull(request.getParameter(LANGUAGE))) {
            if (LOCALE_EN.equals(request.getParameter(LANGUAGE))) {
                session.setAttribute(LOCALE, LOCALE_EN);
            } else if (LOCALE_UA.equals(request.getParameter(LANGUAGE))) {
                session.setAttribute(LOCALE, LOCALE_UA);
            }
        }
        User user = (User) session.getAttribute("user");

        if (user == null) {
            return PAGE_LOGIN;
        } else {
            return COMMAND_HOME;
        }
    }
}
