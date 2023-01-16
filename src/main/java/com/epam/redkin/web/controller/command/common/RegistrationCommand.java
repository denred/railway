package com.epam.redkin.web.controller.command.common;

import com.epam.redkin.model.entity.Role;
import com.epam.redkin.model.entity.User;
import com.epam.redkin.model.exception.IncorrectDataException;
import com.epam.redkin.model.service.UserService;
import com.epam.redkin.model.validator.RegistrationValidator;
import com.epam.redkin.web.controller.command.Command;
import com.epam.redkin.web.listener.AppContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Date;

import static com.epam.redkin.web.controller.Path.*;

public class RegistrationCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationCommand.class);
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("started");
        UserService userService = AppContext.getInstance().getUserService();
        User user = new User();
        RegistrationValidator registrationValidator = new RegistrationValidator();
        HttpSession session = request.getSession();

        user.setEmail(request.getParameter(EMAIL));
        user.setPassword(request.getParameter(PASSWORD));
        user.setFirstName(request.getParameter(FIRST_NAME));
        user.setLastName(request.getParameter(LAST_NAME));
        user.setPhone(request.getParameter(PHONE_NUMBER));
        user.setRole(Role.USER);
        user.setBlocked(false);
        session.setAttribute(SESSION_USER, user);
        try {
            Date birthDate = Date.valueOf(request.getParameter(BIRTH_DATE));
            user.setBirthDate(birthDate.toLocalDate());

        } catch (IllegalArgumentException e) {
            LOGGER.error("Incorrect data entered. Please enter correct birth date.");
            throw new IncorrectDataException("Incorrect data entered, birth date", e);
        }
        registrationValidator.isValidClientRegister(user);
        int id = userService.registr(user);
        user.setUserId(id);
        LOGGER.info("done");
        return PAGE_HOME;
    }
}
