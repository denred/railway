package com.epam.redkin.railway.model.validator;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.HashMap;
import java.util.Map;

public class SearchValidator {
    private static final Logger LOGGER = LoggerFactory.getLogger(SearchValidator.class);
    private static final String DEPARTURE_STATION = "[a-zA-Zа-яА-яёЁ]*\\w*[\\u0400-\\u052F\\u2DE0-\\u2DFF\\uA640-\\uA69F']*-?[a-zA-Zа-яА-яёЁ]*\\w*[\\u0400-\\u052F\\u2DE0-\\u2DFF\\uA640-\\uA69F']*";
    private static final String ARRIVAL_STATION = "[a-zA-Zа-яА-яёЁ]*\\w*[\\u0400-\\u052F\\u2DE0-\\u2DFF\\uA640-\\uA69F']*-?[a-zA-Zа-яА-яёЁ]*\\w*[\\u0400-\\u052F\\u2DE0-\\u2DFF\\uA640-\\uA69F']*";

    public void isValidSearch(String departureStation, String arrivalStation) {
        Map<String, String> errors = new HashMap<>();
        if (StringUtils.isBlank(departureStation) || (!ValidatorUtils.isMatch(DEPARTURE_STATION, departureStation))) {
            errors.put("Incorrect format, type something like \"Dnipro\"", departureStation);
        }
        if (StringUtils.isBlank(arrivalStation) || !ValidatorUtils.isMatch(ARRIVAL_STATION, arrivalStation)) {
            errors.put("Incorrect format, type something like \"Dnipro\"", arrivalStation);
        }
        ValidatorUtils.errorBuilder(errors, LOGGER);
    }
}

