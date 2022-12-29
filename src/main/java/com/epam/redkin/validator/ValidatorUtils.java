package com.epam.redkin.validator;

import java.util.regex.Pattern;

public final class ValidatorUtils {

    private ValidatorUtils() {
    }

    public static boolean isMatch(String regex, String field) {
        return Pattern.compile(regex).matcher(field).matches();
    }
}
