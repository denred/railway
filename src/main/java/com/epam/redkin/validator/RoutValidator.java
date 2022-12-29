package com.epam.redkin.validator;

import com.epam.redkin.model.entity.Route;
import com.epam.redkin.model.exception.IncorrectDataException;
import org.apache.commons.lang3.StringUtils;


import java.util.HashMap;
import java.util.Map;

public class RoutValidator {
   // private static final Logger LOGGER = Logger.getLogger(RoutValidator.class);
    private static final String ROUT_NAME = "[a-zA-Zа-яА-яёЁ]*([ \\-_][a-zA-Zа-яА-яёЁ]+)*";
    private static final String ROUT_NUMBER = "^(?![-\\/\\\\d])(?<!\\d[.,])0*+([\\d-\\/]*)(?![.,]?\\d)$";

    public void isValidRout(Route route) {
        Map<String, String> errors = new HashMap<>();
        if (StringUtils.isBlank(route.getRouteName()) || (!ValidatorUtils.isMatch(ROUT_NAME, route.getRouteName()))) {
            errors.put("Incorrect format, type something like \"Odessa\" or \"Odessa speed\"", route.getRouteName());
        }
        /*if (StringUtils.isBlank(route.getRouteNumber()) || !ValidatorUtils.isMatch(ROUT_NUMBER, route.getRouteNumber())) {
            errors.put("Incorrect format, type something like \"1-23 or 1/23 or 123\"", route.getRouteNumber());
        }*/
        if (!errors.isEmpty()) {
            StringBuilder builder = new StringBuilder();
            for (Map.Entry<String, String> entry : errors.entrySet()) {
                builder.append(entry.getKey())
                        .append("Entered data:&nbsp;")
                        .append(entry.getValue())
                        .append(";<br/>\n");
            }
            IncorrectDataException e = new IncorrectDataException(builder.toString());
           // LOGGER.error(e);
            throw e;
        }
    }
}

