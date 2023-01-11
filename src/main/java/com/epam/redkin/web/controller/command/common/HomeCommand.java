package com.epam.redkin.web.controller.command.common;

import com.epam.redkin.model.entity.User;
import com.epam.redkin.util.constants.AppContextConstant;
import com.epam.redkin.web.controller.command.Command;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class HomeCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(AppContextConstant.SESSION_USER);
        request.setAttribute("role", user.getRole());

        return "WEB-INF/jsp/homePage.jsp";
    }
}
