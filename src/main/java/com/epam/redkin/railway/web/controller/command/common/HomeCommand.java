package com.epam.redkin.railway.web.controller.command.common;

import com.epam.redkin.railway.model.entity.User;
import com.epam.redkin.railway.util.constants.AppContextConstant;
import com.epam.redkin.railway.web.controller.Path;
import com.epam.redkin.railway.web.controller.command.Command;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import static com.epam.redkin.railway.util.constants.AppContextConstant.ROLE;

public class HomeCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(AppContextConstant.SESSION_USER);
        String from = request.getParameter("from");
        String to = request.getParameter("to");

        request.setAttribute("from", from);
        request.setAttribute("to", to);
        request.setAttribute(ROLE, user.getRole());

        return Path.PAGE_HOME;
    }
}
