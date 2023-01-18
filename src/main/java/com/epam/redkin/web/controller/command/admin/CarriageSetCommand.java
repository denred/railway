package com.epam.redkin.web.controller.command.admin;

import com.epam.redkin.model.dto.CarriageDTO;
import com.epam.redkin.model.entity.Carriage;
import com.epam.redkin.model.entity.CarriageType;
import com.epam.redkin.model.entity.Train;
import com.epam.redkin.model.exception.IncorrectDataException;
import com.epam.redkin.model.service.CarriageService;
import com.epam.redkin.model.service.SeatService;
import com.epam.redkin.model.service.TrainService;
import com.epam.redkin.model.validator.CarriageValidator;
import com.epam.redkin.web.controller.command.Command;
import com.epam.redkin.web.listener.AppContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import static com.epam.redkin.util.constants.AppContextConstant.*;
import static com.epam.redkin.web.controller.Path.*;

public class CarriageSetCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(CarriageSetCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("started");
        String forward = PAGE_ADMIN_SET_CARRIAGE;
        TrainService trainService = AppContext.getInstance().getTrainService();
        CarriageService carriageService = AppContext.getInstance().getCarriageService();
        SeatService seatService = AppContext.getInstance().getSeatService();
        CarriageValidator carriageValidator = new CarriageValidator();
        CarriageDTO carriageDTO = new CarriageDTO();

        String carriageId = request.getParameter(CARRIAGE_ID);
        String carriageNumber = request.getParameter(CARRIAGE_NUMBER);
        String trainId = request.getParameter(TRAIN_ID);
        String carriageType = request.getParameter(CARRIAGE_TYPE);
        String countSeats = request.getParameter(COUNT_SEATS);

        if (StringUtils.isNoneBlank(carriageId, carriageNumber, trainId, carriageType, countSeats)) {
            String trainNotSelected = trainId.equals(TRAIN_NOT_SELECTED) ? null : trainId;
            carriageDTO.setCarId(Integer.parseInt(carriageId));
            assert trainNotSelected != null;
            carriageDTO.setTrainId(Integer.parseInt(trainNotSelected));
            Train train = trainService.getTrainById(Integer.parseInt(trainId));
            List<Carriage> carriagesByTrainId = carriageService.getCarByTrainId(train.getId());
            if (containsCarWithCarId(carriagesByTrainId, Integer.parseInt(carriageId))
                    && containsCarWithCarNumber(carriagesByTrainId, carriageNumber)
                    && Integer.parseInt(trainId) == train.getId()) {
                carriageDTO.setCarNumber(carriageNumber);
            } else {
                LOGGER.error("Incorrect data entered");
                throw new IncorrectDataException("Incorrect data entered");
            }
            try {
                carriageDTO.setCarriageType(CarriageType.valueOf(carriageType));
                carriageDTO.setSeats(Integer.valueOf(countSeats));

            } catch (IllegalArgumentException e) {
                LOGGER.error("Incorrect data entered");
                throw new IncorrectDataException("Incorrect data entered", e);
            }
            carriageValidator.isValidCar(carriageDTO);
            carriageService.updateCar(carriageDTO);
            forward = COMMAND_INFO_CARRIAGES;
        } else if (StringUtils.isNoneBlank(trainId, carriageType, countSeats)) {
            String trainNotSelected = trainId.equals(TRAIN_NOT_SELECTED) ? null : trainId;
            assert trainNotSelected != null;
            carriageDTO.setTrainId(Integer.parseInt(trainNotSelected));
            carriageDTO.setCarNumber(carriageNumber);
            try {
                carriageDTO.setCarriageType(CarriageType.valueOf(carriageType));
                carriageDTO.setSeats(Integer.valueOf(countSeats));
            } catch (IllegalArgumentException e) {
                LOGGER.error("Incorrect data entered");
                throw new IncorrectDataException("Incorrect data entered", e);
            }
            carriageValidator.isValidCar(carriageDTO);
            carriageService.addCarriage(carriageDTO);
            forward = COMMAND_INFO_CARRIAGES;
        } else if (StringUtils.isNoneBlank(carriageId)) {
            int carId = Integer.parseInt(carriageId);
            Carriage car = carriageService.getCarById(carId);
            List<Train> trainList = trainService.getAllTrainList();
            List<CarriageType> carTypeList = new ArrayList<>(EnumSet.allOf(CarriageType.class));
            request.setAttribute(CARRIAGE, car);
            request.setAttribute(TRAIN_LIST, trainList);
            request.setAttribute(CARRIAGE_TYPE_LIST, carTypeList);
            int countSeat = seatService.getCountSeat(carId);
            request.setAttribute(COUNT_SEATS, countSeat);
        } else {
            List<Train> trainList = trainService.getAllTrainList();
            request.setAttribute(TRAIN_LIST, trainList);
            List<CarriageType> carTypeList = new ArrayList<>(EnumSet.allOf(CarriageType.class));
            request.setAttribute(CARRIAGE_TYPE_LIST, carTypeList);
        }
        request.setAttribute(FILTER_TRAIN, request.getParameter(FILTER_TRAIN));
        request.setAttribute(FILTER_TYPE_CARRIAGE, request.getParameter(FILTER_TYPE_CARRIAGE));
        LOGGER.info("done");
        return forward;
    }

    public static boolean containsCarWithCarNumber(final List<Carriage> array, final String carNumber) {
        return array.stream().anyMatch(car -> car.getNumber().equals(carNumber));
    }

    public static boolean containsCarWithCarId(final List<Carriage> array, final int carId) {
        return array.stream().anyMatch(car -> car.getCarriageId() == carId);
    }


}
