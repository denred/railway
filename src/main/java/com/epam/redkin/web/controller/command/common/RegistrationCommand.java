package com.epam.redkin.web.controller.command.common;

import com.epam.redkin.model.builder.UserBuilder;
import com.epam.redkin.model.entity.Role;
import com.epam.redkin.model.entity.User;
import com.epam.redkin.model.service.UserService;
import com.epam.redkin.model.validator.RegistrationValidator;
import com.epam.redkin.web.controller.command.Command;
import com.epam.redkin.web.listener.AppContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import static com.epam.redkin.util.constants.AppContextConstant.*;
import static com.epam.redkin.web.controller.Path.*;

public class RegistrationCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("started");
        String forward = PAGE_REGISTRATION;
        String email = request.getParameter(EMAIL);
        String password = request.getParameter(PASSWORD);
        String firstName = request.getParameter(FIRST_NAME);
        String lastName = request.getParameter(LAST_NAME);
        String phoneNumber = request.getParameter(PHONE_NUMBER);
        String birthDate = request.getParameter(BIRTH_DATE);
        if (StringUtils.isNoneBlank(email, password, firstName, lastName, phoneNumber, birthDate)) {
            UserService userService = AppContext.getInstance().getUserService();
            User user = constructUser(request);
            RegistrationValidator registrationValidator = new RegistrationValidator();
            registrationValidator.isValidClientRegister(user);
            HttpSession session = request.getSession();
            int id = userService.registerUser(user, request.getRequestURL().toString());
            session.setAttribute(SESSION_USER, user);
            user.setUserId(id);
            forward = PAGE_LOGIN;
        }
        LOGGER.info("done");
        return forward;
    }

    private User constructUser(HttpServletRequest request) {
        String email = request.getParameter(EMAIL).trim();
        String password = request.getParameter(PASSWORD).trim();
        String firstName = request.getParameter(FIRST_NAME).trim();
        String lastName = request.getParameter(LAST_NAME).trim();
        String phoneNumber = request.getParameter(PHONE_NUMBER).trim();
        String birthDate = request.getParameter(BIRTH_DATE).trim();

        return new UserBuilder()
                .setEmail(email)
                .setPassword(password)
                .setFirstName(firstName)
                .setLastName(lastName)
                .setBirthDate(birthDate)
                .setPhone(phoneNumber)
                .setRole(Role.USER)
                .setBlocked(true)
                .build();
    }
}
