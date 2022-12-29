package com.epam.redkin.web.controller;


import com.epam.redkin.util.constants.AppContextConstant;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Objects;

@WebServlet("/change_language")
public class ChangeLanguageController extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChangeLanguageController.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        if (!Objects.isNull(request.getParameter("lang"))) {
            if (AppContextConstant.LOCALE_EN.equals(request.getParameter("lang"))) {
                session.setAttribute("locale", AppContextConstant.LOCALE_EN);
            } else if (AppContextConstant.LOCALE_RU.equals(request.getParameter("lang"))) {
                session.setAttribute("locale", AppContextConstant.LOCALE_RU);
            }
        }
        if (session.isNew()) {
            response.sendRedirect("login.jsp");
        } else {
            response.sendRedirect(request.getHeader("Referer"));
        }
    }

    @Override
    public void init(ServletConfig config)  {
       // LOGGER.trace("change_language Servlet init");

    }
}
