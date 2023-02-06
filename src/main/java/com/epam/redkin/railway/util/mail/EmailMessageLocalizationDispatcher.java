package com.epam.redkin.railway.util.mail;

import com.epam.redkin.railway.model.exception.IncorrectDataException;
import com.epam.redkin.railway.web.controller.command.SupportedLocaleStorage;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class EmailMessageLocalizationDispatcher {
    private static final Logger LOGGER = LoggerFactory.getLogger(EmailMessageLocalizationDispatcher.class);
    private static final String RESOURCE_NAME = "emailMessages";

    public String getLocalizedMessage(String key, String... messageArgs) {
        LOGGER.info("Started --> public String getLocalizedMessage(String key, String... messageArgs) --> " +
                "key: " + key + ", messageArgs: " + Arrays.toString(messageArgs));
        if (StringUtils.isBlank(key) || StringUtils.isAnyEmpty(messageArgs)) {
            LOGGER.error("Invalid message arguments");
            throw new IncorrectDataException("Invalid message arguments");
        }
        StringBuilder message = new StringBuilder();
        SupportedLocaleStorage locale = SupportedLocaleStorage.getLocaleFromLanguage(Locale.getDefault().getLanguage());
        LOGGER.info("Supported locale: " + locale);
        ResourceBundle resourceBundle = ResourceBundle.getBundle(RESOURCE_NAME, locale.getLocale());
        String[] localizedArgs = getLocalizedMessageArgs(resourceBundle, messageArgs);
        String formattedMessageFragment = String.format(resourceBundle.getString(key), String.join("\n", localizedArgs));
        message.append(formattedMessageFragment).append('\n');
        return message.toString();
    }

    private String[] getLocalizedMessageArgs(ResourceBundle resourceBundle, String... messageArgs) {
        String[] localizedArgs = new String[messageArgs.length];
        for (int i = 0; i < localizedArgs.length; i++) {
            try {
                String arg = resourceBundle.getString(messageArgs[i]);
                localizedArgs[i] = arg;
            } catch (IncorrectDataException | MissingResourceException e) {
                localizedArgs[i] = messageArgs[i];
            }
        }
        return localizedArgs;
    }
}