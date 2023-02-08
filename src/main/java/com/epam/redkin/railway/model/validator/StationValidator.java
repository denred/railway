package com.epam.redkin.railway.model.validator;

import com.epam.redkin.railway.model.entity.Station;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class StationValidator {
    private static final Logger LOGGER = LoggerFactory.getLogger(StationValidator.class);
    private static final String STATION_NAME = "^[a-zA-Zа-яА-яёЁ\\u0400-\\u052F\\u2DE0-\\u2DFF\\uA640-\\uA69F'ʼ`]{4,25}+$";

    public void isValidStation(Station station) {
        Map<String, String> errors = new HashMap<>();
        if (StringUtils.isBlank(station.getStation()) || !ValidatorUtils.isMatch(STATION_NAME, station.getStation())) {
            errors.put("Incorrect format, type something like \"Odessa\"", station.getStation());
        }
        ValidatorUtils.errorBuilder(errors, LOGGER);
    }
}
