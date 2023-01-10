package com.epam.redkin.validator;


import com.epam.redkin.model.exception.IncorrectDataException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.HashMap;
import java.util.Map;


public class LoginValidator {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginValidator.class.getName());
    private static final String EMAIL = "[a-zA-Z0-9._-][a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}";
    private static final String PASSWORD = "^(?:(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*)[^\\s]{8,}$";


    public void isValid(String email, String password) {
        Map<String, String> errors = new HashMap<>();
        if (!ValidatorUtils.isMatch(EMAIL, email)) {
            errors.put("Incorrect format email entered", email);
        }
        if (!ValidatorUtils.isMatch(PASSWORD, password)) {
            errors.put("Incorrect format password entered", password);
        }

        if (!errors.isEmpty()) {
            StringBuilder builder = new StringBuilder();
            for (Map.Entry<String, String> entry : errors.entrySet()) {
                builder.append(entry.getKey())
                        .append("Entered data:&nbsp;")
                        .append(entry.getValue())
                        .append(";<br/>\n");
            }
            IncorrectDataException e = new IncorrectDataException(builder.toString());
            LOGGER.error(e.getMessage());
            throw e;
        }
    }
}
