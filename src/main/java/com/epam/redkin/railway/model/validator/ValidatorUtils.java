package com.epam.redkin.railway.model.validator;

import com.epam.redkin.railway.model.exception.IncorrectDataException;
import org.slf4j.Logger;

import java.util.Map;
import java.util.regex.Pattern;

public final class ValidatorUtils {

    private ValidatorUtils() {
    }

    public static boolean isMatch(String regex, String field) {
        return Pattern.compile(regex).matcher(field).matches();
    }

    static void errorBuilder(Map<String, String> errors, Logger logger) {
        if (!errors.isEmpty()) {
            StringBuilder builder = new StringBuilder();
            for (Map.Entry<String, String> entry : errors.entrySet()) {
                builder.append(entry.getKey())
                        .append("Entered data:&nbsp;")
                        .append(entry.getValue())
                        .append(";<br/>\n");
            }
            IncorrectDataException e = new IncorrectDataException(builder.toString());
            logger.error(e.getMessage(), e);
            throw e;
        }
    }
}
