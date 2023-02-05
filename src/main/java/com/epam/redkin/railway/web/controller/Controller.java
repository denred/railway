package com.epam.redkin.railway.web.controller;

import com.epam.redkin.railway.web.controller.command.ActionFactory;
import com.epam.redkin.railway.web.controller.command.Command;
import com.epam.redkin.railway.web.controller.command.Router;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class Controller extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //define command from jsp
        ActionFactory client = ActionFactory.getInstance();
        Command command = client.defineCommand(request);
        //call to the execute method and return response page
        Router router = command.execute(request, response);
        if (router.getRouteType().equals(Router.RouteType.FORWARD)) {
            RequestDispatcher dispatcher = request.getRequestDispatcher(router.getPagePath());
            dispatcher.forward(request, response);
        } else {
            response.sendRedirect(router.getPagePath());
        }

    }
}
