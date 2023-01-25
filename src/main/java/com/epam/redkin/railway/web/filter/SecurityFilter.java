package com.epam.redkin.railway.web.filter;


import com.epam.redkin.railway.model.entity.Role;
import com.epam.redkin.railway.model.entity.User;
import com.epam.redkin.railway.model.exception.ForbiddenException;
import com.epam.redkin.railway.model.exception.UnauthorizedException;
import com.epam.redkin.railway.util.constants.AppContextConstant;
import com.epam.redkin.railway.web.controller.command.admin.*;
import com.epam.redkin.railway.web.controller.command.common.*;
import com.epam.redkin.railway.web.controller.command.user.ForgetPasswordEmailSendingCommand;
import com.epam.redkin.railway.web.controller.command.user.LoginByTokenLinkCommand;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;

public class SecurityFilter implements Filter {
    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityFilter.class);

    private Set<String> securedUris;
    private Map<Role, Set<String>> accessMap;
    private String encoding;

    @Override
    public void init(FilterConfig filterConfig) {
        encoding = filterConfig.getServletContext().getInitParameter("encoding");

        accessMap = new HashMap<>();

        Set<String> adminAvailableEndpoints = new HashSet<>();
        adminAvailableEndpoints.add("routes");
        adminAvailableEndpoints.add("add_route");
        adminAvailableEndpoints.add("edit_route");
        adminAvailableEndpoints.add("delete_route");
        adminAvailableEndpoints.add("route_mapping");
        adminAvailableEndpoints.add("route_mapping_set_station");
        adminAvailableEndpoints.add("route_mapping_remove_station");
        adminAvailableEndpoints.add("stations");
        adminAvailableEndpoints.add("set_station");
        adminAvailableEndpoints.add("delete_station");
        adminAvailableEndpoints.add("carriages");
        adminAvailableEndpoints.add("trains");
        adminAvailableEndpoints.add("admin_orders");
        adminAvailableEndpoints.add("users");
        adminAvailableEndpoints.add("block");
        adminAvailableEndpoints.add("order_status");
        adminAvailableEndpoints.add("set_train");
        adminAvailableEndpoints.add("remove_train");
        adminAvailableEndpoints.add("set_carriage");
        adminAvailableEndpoints.add("remove_carriage");

        accessMap.put(Role.ADMIN, adminAvailableEndpoints);

        Set<String> userAvailableEndpoints = new HashSet<>();
        userAvailableEndpoints.add("search_routes");
        userAvailableEndpoints.add("orders");
        userAvailableEndpoints.add("route");
        userAvailableEndpoints.add("select_station_and_carriage_type");
        userAvailableEndpoints.add("select_carriage_and_count_seats");
        userAvailableEndpoints.add("select_seats");
        userAvailableEndpoints.add("confirm_order");
        userAvailableEndpoints.add("create_order");
        userAvailableEndpoints.add("cancel_order");
        userAvailableEndpoints.add("sendForgetPasswordData");
        userAvailableEndpoints.add("login_by_token_link");
        userAvailableEndpoints.add("postRegistrationAccountApproval");


        accessMap.put(Role.USER, userAvailableEndpoints);

        securedUris = new HashSet<>();

        for (Set<String> uriSet : accessMap.values()) {
            securedUris.addAll(uriSet);
        }

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        req.setCharacterEncoding(encoding);
        resp.setCharacterEncoding(encoding);
        String commandName = request.getParameter("action");

        if (!securedUris.contains(commandName)) {
            chain.doFilter(request, response);
            return;
        }

        User user = (User) request.getSession().getAttribute(AppContextConstant.SESSION_USER);
        System.out.println(user.toString());
        if (Objects.isNull(user)) {
            String message = "You are not authorized. Please register to enter the site.";
            LOGGER.error(message);
            throw new UnauthorizedException(message);
        }

        Set<String> availableURI = accessMap.get(user.getRole());
        if (availableURI.contains(commandName)) {
            chain.doFilter(req, resp);
            return;
        }

        LOGGER.error("Invalid access rights, access denied");
        throw new ForbiddenException("Invalid access rights, access denied");
    }

    @Override
    public void destroy() {

    }
}
