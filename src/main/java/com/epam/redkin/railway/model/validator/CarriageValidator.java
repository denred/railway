package com.epam.redkin.railway.model.validator;

import com.epam.redkin.railway.model.dto.CarriageDTO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

import static java.util.stream.Collectors.joining;

public class CarriageValidator {
    private static final Logger LOGGER = LoggerFactory.getLogger(CarriageValidator.class);
    private static final String CAR_NUMBER = "^(?<![-\\d])(?<!\\d[.,])\\d*[1-9](?![.,]?\\d){1,2}$";
    private static final String COUNT_OF_SEATS = "^\\d+$";

    public void isValidCar(CarriageDTO carriageDTO) {
        Map<String, String> errors = new HashMap<>();

        if (StringUtils.isBlank(carriageDTO.getCarNumber()) || !ValidatorUtils.isMatch(CAR_NUMBER, carriageDTO.getCarNumber())) {
            errors.put("Incorrect format, type something like \"123\"", carriageDTO.getCarNumber());
        }

        if (StringUtils.isBlank(String.valueOf(carriageDTO.getSeats())) || !ValidatorUtils.isMatch(COUNT_OF_SEATS, String.valueOf(carriageDTO.getSeats()))) {
            errors.put("Incorrect format, type something like \"123\"", String.valueOf(carriageDTO.getSeats()));
        }

        ValidatorUtils.errorBuilder(errors, LOGGER);
    }
}
