package com.epam.redkin.railway.web.filter;


import com.epam.redkin.railway.model.entity.Role;
import com.epam.redkin.railway.model.entity.User;
import com.epam.redkin.railway.model.exception.ForbiddenException;
import com.epam.redkin.railway.model.exception.UnauthorizedException;
import com.epam.redkin.railway.util.constants.AppContextConstant;
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
        adminAvailableEndpoints.add("/administrator_account");
        adminAvailableEndpoints.add("/administrator_add_car");
        adminAvailableEndpoints.add("/administrator_add_rout");
        adminAvailableEndpoints.add("/administrator_add_station");
        adminAvailableEndpoints.add("/administrator_add_train");
        adminAvailableEndpoints.add("/administrator_details_set_rout");
        adminAvailableEndpoints.add("/administrator_edit_info_car");
        adminAvailableEndpoints.add("/administrator_edit_info_details_set_rout");
        adminAvailableEndpoints.add("/administrator_edit_info_order");
        adminAvailableEndpoints.add("/administrator_edit_info_rout");
        adminAvailableEndpoints.add("/administrator_edit_info_station");
        adminAvailableEndpoints.add("/administrator_edit_info_train");
        adminAvailableEndpoints.add("/administrator_set_rout_mapping");
        adminAvailableEndpoints.add("/cancel_order");
        adminAvailableEndpoints.add("/car_delete");
        adminAvailableEndpoints.add("/detail_rout");
        adminAvailableEndpoints.add("/home");
        adminAvailableEndpoints.add("/logout");
        adminAvailableEndpoints.add("/rout_delete");
        adminAvailableEndpoints.add("/remove_rout_mapping");
        adminAvailableEndpoints.add("/search_rout_for_order");
        adminAvailableEndpoints.add("/select_cars_and_seats_for_order");
        adminAvailableEndpoints.add("/select_station_and_car_type_for_order");
        adminAvailableEndpoints.add("/station_delete");
        adminAvailableEndpoints.add("/train_delete");
        adminAvailableEndpoints.add("/user_account");
        adminAvailableEndpoints.add("/user_block");

        accessMap.put(Role.ADMIN, adminAvailableEndpoints);

        Set<String> userAvailableEndpoints = new HashSet<>();
        userAvailableEndpoints.add("/home");
        userAvailableEndpoints.add("/user_account");
        userAvailableEndpoints.add("/search_rout_for_order");
        userAvailableEndpoints.add("/select_cars_and_seats_for_order");
        userAvailableEndpoints.add("/select_station_and_car_type_for_order");
        userAvailableEndpoints.add("/detail_rout");
        userAvailableEndpoints.add("/logout");
        userAvailableEndpoints.add("/cancel_order");


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
        String uri = request.getRequestURI().replace(request.getContextPath(), "");

        if (!securedUris.contains(uri)) {
            chain.doFilter(request, response);
            return;
        }

        User user = (User) request.getSession().getAttribute(AppContextConstant.SESSION_USER);
        if (Objects.isNull(user)) {
            String message = "You are not authorized. Please register to enter the site.";
            LOGGER.error(message);
            throw new UnauthorizedException(message);
        }

        Set<String> availableURI = accessMap.get(user.getRole());
        if (availableURI.contains(uri)) {
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
