package com.epam.redkin.util;

import com.epam.redkin.model.exception.IncorrectDataException;
import com.epam.redkin.web.controller.command.SupportedLocaleStorage;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.ResourceBundle;

public class EmailMessageLocalizationDispatcher {
    private static final String RESOURCE_NAME = "emailMessages";

    public String getLocalizedMessage(String key, String... messageArgs) {
        if (StringUtils.isBlank(key) || StringUtils.isAnyEmpty(messageArgs)) {
            throw new IncorrectDataException("Invalid message arguments");
        }
        StringBuilder message = new StringBuilder();
        for (SupportedLocaleStorage locale : SupportedLocaleStorage.values()) {
            ResourceBundle resourceBundle = ResourceBundle.getBundle(RESOURCE_NAME, locale.getLocale());
            String[] localizedArgs = getLocalizedMessageArgs(resourceBundle, messageArgs);
            String formattedMessageFragment = String.format(resourceBundle.getString(key), (Object) localizedArgs);
            message.append(formattedMessageFragment).append('\n');
        }
        return message.toString();
    }

    private String[] getLocalizedMessageArgs(ResourceBundle resourceBundle, String... messageArgs) {
        String[] localizedArgs = new String[messageArgs.length];
        for (int i = 0; i < localizedArgs.length; i++) {
            try {
                String arg = resourceBundle.getString(messageArgs[i]);
                localizedArgs[i] = arg;
            } catch (IncorrectDataException e) {
                localizedArgs[i] = messageArgs[i];
            }
        }
        return localizedArgs;
    }
}