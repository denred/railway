package com.epam.redkin.validator;

import com.epam.redkin.model.entity.Seat;
import com.epam.redkin.model.exception.IncorrectDataException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.stream.Collectors.joining;

public class SeatValidator {
    private static final Logger LOGGER = LoggerFactory.getLogger(SeatValidator.class);
    private static final String SEAT = "(?<![-\\d])(?<!\\d[.,])\\d*[0-9](?![.,]?\\d){1,2}";

    public static boolean hasDuplicates(List<Seat> seats) {
        Set<Seat> distinctSeatNumbers = new HashSet<>(seats);
        return distinctSeatNumbers.size() != seats.size();
    }


    public void isValidSeat(List<Seat> seats, String countOfSeats) {
        Map<String, String> errors = new HashMap<>();
        int size = seats.size();
        if ((hasDuplicates(seats)) || (seats.isEmpty()) || (Integer.parseInt(countOfSeats) != size)) {
            errors.put("Incorrect format, choose different places", countOfSeats);
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


    public void isValidSeat(String countOfSeats) {
        Map<String, String> errors = new HashMap<>();
        if (StringUtils.isBlank(countOfSeats) || (!ValidatorUtils.isMatch(SEAT, countOfSeats)) || (Integer.parseInt(countOfSeats) == 0)) {
            errors.put("Incorrect format, choose different places", countOfSeats);
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
