package com.epam.redkin.railway.model.validator;

import com.epam.redkin.railway.model.entity.Train;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.HashMap;
import java.util.Map;


public class TrainValidator {
    private static final Logger LOGGER = LoggerFactory.getLogger(TrainValidator.class);
    private static final String TRAIN_NUMBER = "^\\d+\\(?\\w*[\\u0400-\\u052F\\u2DE0-\\u2DFF\\uA640-\\uA69F']*\\)?$";

    public void isValidTrain(Train train) {
        Map<String, String> errors = new HashMap<>();
        if (StringUtils.isBlank(train.getNumber()) || !ValidatorUtils.isMatch(TRAIN_NUMBER, train.getNumber())) {
            errors.put("Incorrect format, type something like \"123\"", train.getNumber());
        }

        ValidatorUtils.errorBuilder(errors, LOGGER);
    }
}