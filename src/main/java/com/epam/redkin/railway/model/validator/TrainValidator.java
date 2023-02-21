package com.epam.redkin.railway.model.validator;

import com.epam.redkin.railway.model.entity.Train;
import com.epam.redkin.railway.web.controller.command.SupportedLocaleStorage;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;


public class TrainValidator {
    private static final Logger LOGGER = LoggerFactory.getLogger(TrainValidator.class);
    private static final String TRAIN_NUMBER = "^\\d*\\(?\\w*[\\u0400-\\u052F\\u2DE0-\\u2DFF\\uA640-\\uA69F']*\\)?$";
    private static final String RESOURCE_NAME = "exceptionMessages";
    private static final String VALIDATION_TRAIN_NUMBER = "validation.train.filter.number";

    public String isValidTrain(Train train) {
        Map<String, String> errors = new HashMap<>();
        SupportedLocaleStorage locale = SupportedLocaleStorage.getLocaleFromLanguage(Locale.getDefault().getLanguage());
        ResourceBundle resourceBundle = ResourceBundle.getBundle(RESOURCE_NAME, locale.getLocale());
        if (StringUtils.isBlank(train.getNumber()) || !ValidatorUtils.isMatch(TRAIN_NUMBER, train.getNumber())) {
            errors.put(resourceBundle.getString(VALIDATION_TRAIN_NUMBER), train.getNumber());
        }

        return ValidatorUtils.errorBuilder(errors, LOGGER);
    }
}