package com.epam.redkin.railway.model.service.impl;

import com.epam.redkin.railway.model.service.SearchService;
import com.epam.redkin.railway.model.validator.ValidatorUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class SearchServiceImpl implements SearchService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SearchServiceImpl.class);
    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9._%+-]*@?[a-zA-Z0-9.-]*\\.?[a-zA-Z]{1,}$";
    private static final String NAME_PATTERN = "[a-zA-Zа-яА-яёЁ\\u0400-\\u052F\\u2DE0-\\u2DFF\\uA640-\\uA69F']{1,60}";
    private static final String PHONE_PATTERN = "\\+?[3]?[8]?[0]?-?[0-9]{2}-?[0-9]{3}-?[0-9]{2}-?[0-9]{2}";
    private static final String DATE_PATTERN = "\\d{4}-\\d{2}-\\d{2}";

    @Override
    public String getParameter(HttpServletRequest request, String param) {
        HttpSession session = request.getSession();
        String search = request.getParameter(param);
        if (search == null) {
            search = (String) session.getAttribute(param);
        } else {
            search = search.replaceAll("[+\\-]", "");
            session.setAttribute(param, search);
        }
        return search;
    }

    @Override
    public void addSearch(Map<String, String> search, String key, String value) {
        if (StringUtils.isNoneBlank(value)) {
            search.put(key, value);
        }
    }

    @Override
    public boolean isFieldsInvalid(String email, String firstName, String lastName, String phone, String birthDate) {
        Map<String, String> errors = new HashMap<>();
        if (StringUtils.isNotBlank(email) && !ValidatorUtils.isMatch(EMAIL_PATTERN, email)) {
            errors.put("Incorrect email format", email);
        }

        if (StringUtils.isNotBlank(firstName) && !ValidatorUtils.isMatch(NAME_PATTERN, firstName)) {
            errors.put("Incorrect first name format", firstName);
        }

        if (StringUtils.isNotBlank(lastName) && !ValidatorUtils.isMatch(NAME_PATTERN, lastName)) {
            errors.put("Incorrect last name format", lastName);
        }

        if (StringUtils.isNotBlank(phone) && !ValidatorUtils.isMatch(PHONE_PATTERN, phone)) {
            errors.put("Incorrect phone number format", phone);
        }

        if (StringUtils.isNotBlank(birthDate) && !ValidatorUtils.isMatch(DATE_PATTERN, birthDate)) {
            errors.put("Incorrect date format", birthDate);
        }

        String errorMessage = ValidatorUtils.errorBuilder(errors, LOGGER);

        return StringUtils.isNotBlank(errorMessage);
    }
}
