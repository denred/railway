package com.epam.redkin.railway.model.service.impl;

import com.epam.redkin.railway.model.service.SearchService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

public class SearchServiceImpl implements SearchService {
    @Override
    public String getParameter(HttpServletRequest request, String param) {
        String search = request.getParameter(param);
        HttpSession session = request.getSession();
        if (search == null) {
            search = (String) session.getAttribute(param);
        } else {
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
}
