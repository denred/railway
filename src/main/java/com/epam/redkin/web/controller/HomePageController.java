package com.epam.redkin.web.controller;

import com.epam.redkin.model.entity.User;
import com.epam.redkin.util.constants.AppContextConstant;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@WebServlet("/home")
public class HomePageController extends HttpServlet {
   private static final Logger LOGGER = LoggerFactory.getLogger(HomePageController.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(AppContextConstant.SESSION_USER);
        request.setAttribute("role", user.getRole());

        request.getRequestDispatcher("WEB-INF/jsp/homePage.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOGGER.trace("home Servlet init");
    }
}
