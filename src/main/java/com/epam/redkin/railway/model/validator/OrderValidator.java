package com.epam.redkin.railway.model.validator;

import com.epam.redkin.railway.model.dto.BookingDTO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.HashMap;
import java.util.Map;


public class OrderValidator {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderValidator.class);
    private static final String SEATS = "(?<![-\\d])(?<!\\d[.,])\\d*[0-9](?![.,]?\\d){1,2}";

    public void isValidOrder(BookingDTO order) {
        Map<String, String> errors = new HashMap<>();
        ValidatorUtils.errorBuilder(errors, LOGGER);
    }
}
