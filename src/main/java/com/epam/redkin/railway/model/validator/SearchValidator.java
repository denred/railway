package com.epam.redkin.railway.model.validator;

import com.epam.redkin.railway.web.controller.command.SupportedLocaleStorage;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;


public class SearchValidator {
    private static final Logger LOGGER = LoggerFactory.getLogger(SearchValidator.class);
    private static final String DEPARTURE_STATION = "[a-zA-Zа-яА-яёЁ]*\\w*[\\u0400-\\u052F\\u2DE0-\\u2DFF\\uA640-\\uA69F'ʼ`]*-?[a-zA-Zа-яА-яёЁ]*\\w*[\\u0400-\\u052F\\u2DE0-\\u2DFF\\uA640-\\uA69F'ʼ`]*";
    private static final String ARRIVAL_STATION = "[a-zA-Zа-яА-яёЁ]*\\w*[\\u0400-\\u052F\\u2DE0-\\u2DFF\\uA640-\\uA69F'ʼ`]*-?[a-zA-Zа-яА-яёЁ]*\\w*[\\u0400-\\u052F\\u2DE0-\\u2DFF\\uA640-\\uA69F'ʼ`]*";
    private static final String RESOURCE_NAME = "exceptionMessages";
    private static final String VALIDATION_DEPARTURE_STATION = "validation.departure.station";
    private static final String VALIDATION_ARRIVAL_STATION = "validation.arrival.station";


    public String isValidSearch(String departureStation, String arrivalStation) {
        Map<String, String> errors = new HashMap<>();
        SupportedLocaleStorage locale = SupportedLocaleStorage.getLocaleFromLanguage(Locale.getDefault().getLanguage());
        ResourceBundle resourceBundle = ResourceBundle.getBundle(RESOURCE_NAME, locale.getLocale());
        if (StringUtils.isBlank(departureStation) || (!ValidatorUtils.isMatch(DEPARTURE_STATION, departureStation))) {
            errors.put(resourceBundle.getString(VALIDATION_DEPARTURE_STATION), departureStation);
        }
        if (StringUtils.isBlank(arrivalStation) || !ValidatorUtils.isMatch(ARRIVAL_STATION, arrivalStation)) {
            errors.put(resourceBundle.getString(VALIDATION_ARRIVAL_STATION), arrivalStation);
        }
        return ValidatorUtils.errorBuilder(errors, LOGGER);
    }
}

