package com.epam.redkin.validator;

import com.epam.redkin.model.entity.Order;
import com.epam.redkin.model.exception.IncorrectDataException;
import org.apache.commons.lang3.StringUtils;


import java.util.HashMap;
import java.util.Map;

import static java.util.stream.Collectors.joining;

public class OrderValidator {
    //private static final Logger LOGGER = Logger.getLogger(OrderValidator.class);
    private static final String SEATS = "(?<![-\\d])(?<!\\d[.,])\\d*[0-9](?![.,]?\\d){1,2}";

    public void isValidOrder(Order order) {
        Map<String, String> errors = new HashMap<>();
        if (StringUtils.isBlank(String.valueOf(order.getCountOfSeats())) && !ValidatorUtils.isMatch(SEATS, String.valueOf(order.getCountOfSeats()))) {
            errors.put("Incorrect format, type something like \"1-20\"", String.valueOf(order.getCountOfSeats()));
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
           // LOGGER.error(e);
            throw e;
        }
    }
}
