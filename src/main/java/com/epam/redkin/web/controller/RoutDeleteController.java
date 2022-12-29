package com.epam.redkin.web.controller;


import com.epam.redkin.service.RoutService;
import com.epam.redkin.util.constants.AppContextConstant;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/rout_delete")
public class RoutDeleteController extends HttpServlet {
    //private static final Logger LOGGER = Logger.getLogger(RoutDeleteController.class);

    private RoutService routService;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String routsId = request.getParameter("routs_id");
        routService.removeRout(Integer.parseInt(routsId));
        response.sendRedirect("administrator_account");
    }

    @Override
    public void init(ServletConfig config) {
        routService = (RoutService) config.getServletContext().getAttribute(AppContextConstant.ROUT_SERVICE);
        //LOGGER.trace("rout_delete Servlet init");
    }
}
