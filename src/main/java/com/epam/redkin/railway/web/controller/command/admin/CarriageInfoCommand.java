package com.epam.redkin.railway.web.controller.command.admin;

import com.epam.redkin.railway.model.dto.CarriageDTO;
import com.epam.redkin.railway.model.entity.CarriageType;
import com.epam.redkin.railway.model.service.CarriageService;
import com.epam.redkin.railway.util.constants.AppContextConstant;
import com.epam.redkin.railway.web.controller.Path;
import com.epam.redkin.railway.web.controller.command.Command;
import com.epam.redkin.railway.appcontext.AppContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class CarriageInfoCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(CarriageInfoCommand.class);
    private static final int RECORDS_PER_PAGE = 5;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("started");
        CarriageService carriageService = AppContext.getInstance().getCarriageService();
        List<CarriageType> carriageTypeList = new ArrayList<>(EnumSet.allOf(CarriageType.class));
        int page = 1;
        if (request.getParameter(AppContextConstant.PAGE) != null)
            page = Integer.parseInt(request.getParameter(AppContextConstant.PAGE));
        String trainFilter = request.getParameter(AppContextConstant.FILTER_TRAIN);
        String carriageFilter = request.getParameter(AppContextConstant.FILTER_TYPE_CARRIAGE);
        List<CarriageDTO> carriageDtoList = carriageService.getCarriageDtoListByCurrentRecordAndRecordsPerPage(
                (page - 1) * RECORDS_PER_PAGE,
                RECORDS_PER_PAGE * page,
                trainFilter, carriageFilter);
        int noOfRecords = carriageService.getRouteListSize();
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / RECORDS_PER_PAGE);
        request.setAttribute(AppContextConstant.PAGE_RECORDS, RECORDS_PER_PAGE);
        request.setAttribute(AppContextConstant.PAGE_COUNT, noOfPages);
        request.setAttribute(AppContextConstant.CURRENT_PAGE, page);
        request.setAttribute(AppContextConstant.CARRIAGE_TYPE_LIST, carriageTypeList);
        request.setAttribute(AppContextConstant.FILTER_TRAIN, trainFilter);
        request.setAttribute(AppContextConstant.FILTER_TYPE_CARRIAGE, carriageFilter);
        request.setAttribute(AppContextConstant.CARRIAGE_DTO_LIST, carriageDtoList);
        LOGGER.info("done");
        return Path.PAGE_ADMIN_INFO_CARRIAGE;
    }
}