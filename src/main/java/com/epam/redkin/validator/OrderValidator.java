package com.epam.redkin.validator;

import com.epam.redkin.model.entity.Order;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.HashMap;
import java.util.Map;


public class OrderValidator {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderValidator.class);
    private static final String SEATS = "(?<![-\\d])(?<!\\d[.,])\\d*[0-9](?![.,]?\\d){1,2}";

    public void isValidOrder(Order order) {
        Map<String, String> errors = new HashMap<>();
        if (StringUtils.isBlank(String.valueOf(order.getCountOfSeats())) && !ValidatorUtils.isMatch(SEATS, String.valueOf(order.getCountOfSeats()))) {
            errors.put("Incorrect format, type something like \"1-20\"", String.valueOf(order.getCountOfSeats()));
        }
        ValidatorUtils.errorBuilder(errors, LOGGER);
    }
}
