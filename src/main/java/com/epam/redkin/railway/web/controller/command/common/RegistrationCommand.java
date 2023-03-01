package com.epam.redkin.railway.web.controller.command.common;

import com.epam.redkin.railway.model.entity.Role;
import com.epam.redkin.railway.model.entity.User;
import com.epam.redkin.railway.model.service.UserService;
import com.epam.redkin.railway.model.validator.RegistrationValidator;
import com.epam.redkin.railway.web.controller.command.Command;
import com.epam.redkin.railway.appcontext.AppContext;
import com.epam.redkin.railway.web.controller.command.Router;
import com.epam.redkin.railway.web.controller.command.SupportedLocaleStorage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.Locale;

import static com.epam.redkin.railway.util.constants.AppContextConstant.*;
import static com.epam.redkin.railway.web.controller.Path.COMMAND_GET_LOGIN_PAGE;
import static com.epam.redkin.railway.web.controller.Path.PAGE_REGISTRATION;

public class RegistrationCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationCommand.class);
    private final AppContext appContext;

    public RegistrationCommand(AppContext appContext) {
        this.appContext = appContext;
    }

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("started");


        HttpSession session = request.getSession();
        String locale = SupportedLocaleStorage.getLocaleFromLanguage(Locale.getDefault().getLanguage()).getLanguage();
        session.setAttribute(LOCALE, locale);

        String email = request.getParameter(EMAIL);
        String password = request.getParameter(PASSWORD);
        String firstName = request.getParameter(FIRST_NAME);
        String lastName = request.getParameter(LAST_NAME);
        String phoneNumber = request.getParameter(PHONE_NUMBER);
        String birthDate = request.getParameter(BIRTH_DATE);

        if (StringUtils.isNoneBlank(email, password, firstName, lastName, phoneNumber, birthDate)) {
            UserService userService = appContext.getUserService();

            User user = User.builder()
                    .email(email.trim())
                    .password(password.trim())
                    .firstName(firstName.trim())
                    .lastName(lastName.trim())
                    .birthDate(LocalDate.parse(birthDate))
                    .phone(phoneNumber.trim())
                    .role(Role.USER)
                    .blocked(true).build();

            RegistrationValidator registrationValidator = new RegistrationValidator();
            registrationValidator.isValidClientRegister(user);
            LOGGER.info("User successfully authenticated: {}", user);

            int userId = userService.registerUser(user, request.getRequestURL().toString());
            user.setUserId(userId);

            session.setAttribute(SESSION_USER, user);

            return Router.redirect(COMMAND_GET_LOGIN_PAGE);
        }

        LOGGER.info("done");
        return Router.forward(PAGE_REGISTRATION);
    }

}
