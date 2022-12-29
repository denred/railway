package com.epam.redkin.validator;

import com.epam.redkin.model.entity.Station;
import com.epam.redkin.model.exception.IncorrectDataException;
import org.apache.commons.lang3.StringUtils;


import java.util.HashMap;
import java.util.Map;

import static java.util.stream.Collectors.joining;

public class StationValidator {
    //private static final Logger LOGGER = Logger.getLogger(StationValidator.class);
    private static final String STATION_NAME = "^[a-zA-Zа-яА-яёЁ]{4,25}+$";

    public void isValidStation(Station station) {
        Map<String, String> errors = new HashMap<>();
        if (StringUtils.isBlank(station.getStation()) || !ValidatorUtils.isMatch(STATION_NAME, station.getStation())) {
            errors.put("Incorrect format, type something like \"Odessa\"", station.getStation());
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
            //LOGGER.error(e);
            throw e;
        }
    }
}
