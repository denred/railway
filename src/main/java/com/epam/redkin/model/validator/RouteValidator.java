package com.epam.redkin.model.validator;

import com.epam.redkin.model.entity.Route;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.HashMap;
import java.util.Map;

public class RouteValidator {
    private static final Logger LOGGER = LoggerFactory.getLogger(RouteValidator.class);
    private static final String ROUT_NAME = "[a-zA-Zа-яА-яёЁ\\u0400-\\u052F\\u2DE0-\\u2DFF\\uA640-\\uA69F']*([ \\-_][a-zA-Zа-яА-яёЁ\\u0400-\\u052F\\u2DE0-\\u2DFF\\uA640-\\uA69F']+)*";

    public void isValidRout(Route route) {
        Map<String, String> errors = new HashMap<>();
        if (StringUtils.isBlank(route.getRouteName()) || (!ValidatorUtils.isMatch(ROUT_NAME, route.getRouteName()))) {
            errors.put("Incorrect format, type something like \"Dnipro\" or \"Дніпро-Київ\"", route.getRouteName());
        }

        ValidatorUtils.errorBuilder(errors, LOGGER);
    }
}

