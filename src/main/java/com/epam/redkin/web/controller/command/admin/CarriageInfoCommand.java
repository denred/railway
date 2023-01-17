package com.epam.redkin.web.controller.command.admin;

import com.epam.redkin.model.dto.CarriageDTO;
import com.epam.redkin.model.entity.CarriageType;
import com.epam.redkin.model.service.CarriageService;
import com.epam.redkin.web.controller.command.Command;
import com.epam.redkin.web.listener.AppContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import static com.epam.redkin.web.controller.Path.*;

public class CarriageInfoCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(CarriageInfoCommand.class);
    private static final int RECORDS_PER_PAGE = 5;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("started");
        CarriageService carriageService = AppContext.getInstance().getCarriageService();
        List<CarriageType> carriageTypeList = new ArrayList<>(EnumSet.allOf(CarriageType.class));
        int page = 1;
        if (request.getParameter("page") != null)
            page = Integer.parseInt(request.getParameter("page"));
        String trainFilter = request.getParameter(FILTER_TRAIN);
        String carriageFilter = request.getParameter(FILTER_TYPE_CARRIAGE);
        List<CarriageDTO> carriageDtoList = carriageService.getCarriageDtoListByCurrentRecordAndRecordsPerPage(
                (page - 1) * RECORDS_PER_PAGE,
                RECORDS_PER_PAGE * page,
                trainFilter, carriageFilter);
        int noOfRecords = carriageService.getRouteListSize();
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / RECORDS_PER_PAGE);
        request.setAttribute("recordsPerPage", RECORDS_PER_PAGE);
        request.setAttribute("noOfPages", noOfPages);
        request.setAttribute("currentPage", page);
        request.setAttribute("carTypeList", carriageTypeList);
        request.setAttribute(FILTER_TRAIN, trainFilter);
        request.setAttribute(FILTER_TYPE_CARRIAGE, carriageFilter);
        request.setAttribute("currentPage", page);
        request.setAttribute("car_list", carriageDtoList);
        LOGGER.info("done");
        return PAGE_ADMIN_INFO_CARRIAGE;
    }
}
