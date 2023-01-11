package com.epam.redkin.web.controller.command.common;

import com.epam.redkin.model.entity.User;
import com.epam.redkin.service.UserService;
import com.epam.redkin.web.controller.Path;
import com.epam.redkin.web.controller.command.Command;
import com.epam.redkin.web.listener.AppContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class LoginCommand implements Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        UserService userService = AppContext.getInstance().getUserService();
        HttpSession session = request.getSession();

        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String errorMessage;
        String forward = Path.PAGE_LOGIN;

        if (login == null || password == null || login.isEmpty() || password.isEmpty()) {
            errorMessage = "Login or password can't be empty";
            request.setAttribute("errorMessage", errorMessage);
            return forward;
        }

        User user = userService.isValidUser(login, password);
        forward = Path.COMMAND_HOME;
        session.setAttribute("user", user);

        return forward;
    }
}
