package com.epam.redkin.validator;

import com.epam.redkin.model.entity.Route;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.HashMap;
import java.util.Map;

public class RouteValidator {
    private static final Logger LOGGER = LoggerFactory.getLogger(RouteValidator.class);
    private static final String ROUT_NAME = "[a-zA-Zа-яА-яёЁ]*([ \\-_][a-zA-Zа-яА-яёЁ]+)*";

    public void isValidRout(Route route) {
        Map<String, String> errors = new HashMap<>();
        if (StringUtils.isBlank(route.getRouteName()) || (!ValidatorUtils.isMatch(ROUT_NAME, route.getRouteName()))) {
            errors.put("Incorrect format, type something like \"Odessa\" or \"Odessa speed\"", route.getRouteName());
        }

        ValidatorUtils.errorBuilder(errors, LOGGER);
    }
}

