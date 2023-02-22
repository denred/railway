package com.epam.redkin.railway.model.service.impl;

import com.epam.redkin.railway.model.service.PaginationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import static com.epam.redkin.railway.util.constants.AppContextConstant.*;
import static com.epam.redkin.railway.util.constants.AppContextConstant.CURRENT_PAGE;

public class PaginationServiceImpl implements PaginationService {

    @Override
    public void setPaginationParameter(HttpServletRequest request, int currentPage, int records, int pageRecords, int firstVisibleLinks) {
        int pagesCount = (int) Math.ceil(records * 1.0 / pageRecords);
        int lastPage = pagesCount;
        pagesCount = Math.min(pagesCount, firstVisibleLinks);
        HttpSession session = request.getSession();

        session.setAttribute(PAGE_RECORDS, pageRecords);
        session.setAttribute(PAGE_COUNT, pagesCount);
        session.setAttribute(LAST_PAGE, lastPage);
        session.setAttribute(CURRENT_PAGE, currentPage);
    }

    @Override
    public int getPage(HttpServletRequest request) {
        int page = 1;
        if (request.getParameter(PAGE) != null) {
            page = Integer.parseInt(request.getParameter(PAGE));
        }
        return page;
    }
}
