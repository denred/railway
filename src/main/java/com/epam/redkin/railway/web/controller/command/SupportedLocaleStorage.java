package com.epam.redkin.railway.web.controller.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;

public enum SupportedLocaleStorage {
    ENG(new Locale("en")),
    UA(new Locale("ua"));

    private static final Logger LOGGER = LoggerFactory.getLogger(SupportedLocaleStorage.class);

    private final Locale locale;

    SupportedLocaleStorage(Locale locale) {
        this.locale = locale;
    }

    public static SupportedLocaleStorage getLocaleFromLanguage(String inputLanguage) {
        for (SupportedLocaleStorage currentLocale: SupportedLocaleStorage.values()) {
            if (currentLocale.locale.getLanguage().equals(inputLanguage)) {
                return currentLocale;
            }
        }
        LOGGER.warn(String.format("locale %s is not found, default locale is %s", inputLanguage, ENG.locale.toString()));
        return ENG;
    }

    public String getLanguage() {
        return locale.getLanguage();
    }

    public Locale getLocale() {
        return locale;
    }
}
