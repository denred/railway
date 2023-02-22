package com.epam.redkin.railway.model.service;

import jakarta.servlet.http.HttpServletRequest;

public interface PaginationService {
    void setPaginationParameter(HttpServletRequest request, int currentPage, int records, int pageRecords, int firstVisibleLinks);

    int getPage(HttpServletRequest request);
}
