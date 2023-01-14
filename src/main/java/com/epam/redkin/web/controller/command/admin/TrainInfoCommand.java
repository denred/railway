package com.epam.redkin.web.controller.command.admin;

import com.epam.redkin.model.entity.Train;
import com.epam.redkin.model.service.TrainService;
import com.epam.redkin.web.controller.command.Command;
import com.epam.redkin.web.listener.AppContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static com.epam.redkin.web.controller.Path.PAGE_ADMIN_INFO_TRAIN;

public class TrainInfoCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(TrainInfoCommand.class);
    private static final int RECORDS_PER_PAGE = 5;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("started");
        TrainService trainService = AppContext.getInstance().getTrainService();
        int page = 1;
        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }
        int noOfRecords = trainService.getTrainListSize();
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / RECORDS_PER_PAGE);
        List<Train> trainList = trainService.getTrainListBySetRecords(
                (page - 1) * RECORDS_PER_PAGE,
                RECORDS_PER_PAGE * page);
        request.setAttribute("recordsPerPage", RECORDS_PER_PAGE);
        request.setAttribute("noOfPages", noOfPages);
        request.setAttribute("currentPage", page);
        request.setAttribute("train_list", trainList);
        LOGGER.info("done");
        return PAGE_ADMIN_INFO_TRAIN;
    }
}
