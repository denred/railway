package com.epam.redkin.railway.model.validator;

import org.slf4j.Logger;

import java.util.Map;
import java.util.regex.Pattern;

public final class ValidatorUtils {

    private ValidatorUtils() {
    }

    public static boolean isMatch(String regex, String field) {
        return Pattern.compile(regex).matcher(field).matches();
    }

    public static String errorBuilder(Map<String, String> errors, Logger logger) {
        StringBuilder builder = new StringBuilder();
        if (!errors.isEmpty()) {
            for (Map.Entry<String, String> entry : errors.entrySet()) {
                builder.append(entry.getKey())
                        .append("<br/>");
            }
            logger.error("Incorrect input: " + builder);
        }
        return builder.toString();
    }
}
