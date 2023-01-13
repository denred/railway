package com.epam.redkin.web.controller.command.common;

import com.epam.redkin.model.entity.User;
import com.epam.redkin.util.constants.AppContextConstant;
import com.epam.redkin.web.controller.command.Command;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import static com.epam.redkin.web.controller.Path.PAGE_HOME;
import static com.epam.redkin.web.controller.Path.SESSION_USER;

public class HomeCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(SESSION_USER);
        request.setAttribute("role", user.getRole());

        return PAGE_HOME;
    }
}
