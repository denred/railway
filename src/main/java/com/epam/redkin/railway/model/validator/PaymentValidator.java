package com.epam.redkin.railway.model.validator;

import com.epam.redkin.railway.model.entity.Payment;
import com.epam.redkin.railway.web.controller.command.SupportedLocaleStorage;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

public class PaymentValidator implements Validator<Payment> {
    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentValidator.class);
    private static final String CARD_HOLDERS_NAME = "^\\w+\\s+\\w+.*$";
    private static final String CARD_NUMBER = "\\d{16}";
    private static final String CARD_EXPIRY_MONTH = "^(0?[1-9]|1[0-2])$";
    private static final String CARD_EXPIRY_YEAR = "\\d{4}";
    private static final String CARD_CVV = "\\d{3}";
    private static final String RESOURCE_NAME = "exceptionMessages";
    private static final String VALIDATION_CARD_HOLDERS_MESSAGE = "validation.payment.card.holders";
    private static final String VALIDATION_CARD_NUMBER = "validation.payment.card.number";
    private static final String VALIDATION_CARD_EXPIRY_MONTH = "validation.payment.expiry.month";
    private static final String VALIDATION_CARD_EXPIRY_YEAR = "validation.payment.expiry.year";
    private static final String VALIDATION_CARD_CVV = "validation.payment.card.cvv";


    @Override
    public String isValid(Payment payment) {
        LOGGER.info("Started payment validation payment={}", payment);
        Map<String, String> errors = new HashMap<>();
        SupportedLocaleStorage locale = SupportedLocaleStorage.getLocaleFromLanguage(Locale.getDefault().getLanguage());
        ResourceBundle resourceBundle = ResourceBundle.getBundle(RESOURCE_NAME, locale.getLocale());

        if (StringUtils.isBlank(payment.getCardHolderName()) || !ValidatorUtils.isMatch(CARD_HOLDERS_NAME, payment.getCardHolderName())) {
            errors.put(resourceBundle.getString(VALIDATION_CARD_HOLDERS_MESSAGE), payment.getCardHolderName());
        }

        if (StringUtils.isEmpty(payment.getExpiryMonth()) || !ValidatorUtils.isMatch(CARD_EXPIRY_MONTH, payment.getExpiryMonth())) {
            errors.put(resourceBundle.getString(VALIDATION_CARD_EXPIRY_MONTH), payment.getExpiryMonth());
        }

        if (StringUtils.isEmpty(payment.getExpiryYear()) || !ValidatorUtils.isMatch(CARD_EXPIRY_YEAR, payment.getExpiryYear())) {
            errors.put(resourceBundle.getString(VALIDATION_CARD_EXPIRY_YEAR), payment.getExpiryYear());
        }

        if (StringUtils.isBlank(payment.getCvvCode()) || !ValidatorUtils.isMatch(CARD_CVV, payment.getCvvCode())) {
            errors.put(resourceBundle.getString(VALIDATION_CARD_CVV), payment.getCvvCode());
        }

        if (StringUtils.isBlank(payment.getCardNumber()) || !ValidatorUtils.isMatch(CARD_NUMBER, payment.getCardNumber())) {
            errors.put(resourceBundle.getString(VALIDATION_CARD_NUMBER), payment.getCardNumber());
        }

        return ValidatorUtils.errorBuilder(errors, LOGGER);
    }
}
