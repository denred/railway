package com.epam.redkin.railway.web.controller.command.common;

import com.epam.redkin.railway.model.entity.Role;
import com.epam.redkin.railway.model.entity.User;
import com.epam.redkin.railway.model.service.UserService;
import com.epam.redkin.railway.model.validator.RegistrationValidator;
import com.epam.redkin.railway.util.constants.AppContextConstant;
import com.epam.redkin.railway.web.controller.Path;
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

public class RegistrationCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationCommand.class);

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("started");
        Router router = new Router();
        router.setRouteType(Router.RouteType.FORWARD);
        router.setPagePath(Path.PAGE_REGISTRATION);
        HttpSession session = request.getSession();
        String locale = SupportedLocaleStorage.getLocaleFromLanguage(Locale.getDefault().getLanguage()).getLanguage();
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
            int id = userService.registerUser(user, request.getRequestURL().toString());
            session.setAttribute(AppContextConstant.SESSION_USER, user);
            user.setUserId(id);
            router.setPagePath(Path.PAGE_LOGIN);
        }

        session.setAttribute(LOCALE, locale);
        LOGGER.info("done");
        return router;
    }

    private User constructUser(HttpServletRequest request) {
        String email = request.getParameter(EMAIL).trim();
        String password = request.getParameter(PASSWORD).trim();
        String firstName = request.getParameter(FIRST_NAME).trim();
        String lastName = request.getParameter(AppContextConstant.LAST_NAME).trim();
        String phoneNumber = request.getParameter(AppContextConstant.PHONE_NUMBER).trim();
        String birthDate = request.getParameter(AppContextConstant.BIRTH_DATE).trim();

        return User.builder()
                .email(email)
                .password(password)
                .firstName(firstName)
                .lastName(lastName)
                .birthDate(LocalDate.parse(birthDate))
                .phone(phoneNumber)
                .role(Role.USER)
                .blocked(true)
                .build();
    }
}
