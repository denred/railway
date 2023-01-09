package com.epam.redkin.validator;

import com.epam.redkin.model.entity.Train;
import com.epam.redkin.model.exception.IncorrectDataException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.HashMap;
import java.util.Map;

import static java.util.stream.Collectors.joining;

public class TrainValidator {
    private static final Logger LOGGER = LoggerFactory.getLogger(TrainValidator.class);
    private static final String TRAIN_NUMBER = "^[\\d]+$";

    public void isValidTrain(Train train) {
        Map<String, String> errors = new HashMap<>();
        if (StringUtils.isBlank(train.getNumber()) || !ValidatorUtils.isMatch(TRAIN_NUMBER, train.getNumber())) {
            errors.put("Incorrect format, type something like \"123\"", train.getNumber());
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