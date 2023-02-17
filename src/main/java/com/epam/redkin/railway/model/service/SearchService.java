package com.epam.redkin.railway.model.service;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

public interface SearchService {
    String getParameter(HttpServletRequest request, String parameter);
    void addSearch(Map<String, String> search, String key, String value);
}
