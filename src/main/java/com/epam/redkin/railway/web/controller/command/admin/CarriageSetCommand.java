package com.epam.redkin.railway.web.controller.command.admin;

import com.epam.redkin.railway.model.dto.CarriageDTO;
import com.epam.redkin.railway.model.entity.Carriage;
import com.epam.redkin.railway.model.entity.CarriageType;
import com.epam.redkin.railway.model.entity.Train;
import com.epam.redkin.railway.model.exception.IncorrectDataException;
import com.epam.redkin.railway.model.service.CarriageService;
import com.epam.redkin.railway.model.service.SeatService;
import com.epam.redkin.railway.model.service.TrainService;
import com.epam.redkin.railway.model.validator.CarriageValidator;
import com.epam.redkin.railway.util.constants.AppContextConstant;
import com.epam.redkin.railway.web.controller.Path;
import com.epam.redkin.railway.web.controller.command.Command;
import com.epam.redkin.railway.appcontext.AppContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class CarriageSetCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(CarriageSetCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("started");
        String forward = Path.PAGE_ADMIN_SET_CARRIAGE;
        TrainService trainService = AppContext.getInstance().getTrainService();
        CarriageService carriageService = AppContext.getInstance().getCarriageService();
        SeatService seatService = AppContext.getInstance().getSeatService();
        CarriageValidator carriageValidator = new CarriageValidator();
        CarriageDTO carriageDTO = CarriageDTO.builder().build();

        String carriageId = request.getParameter(AppContextConstant.CARRIAGE_ID);
        String carriageNumber = request.getParameter(AppContextConstant.CARRIAGE_NUMBER);
        String trainId = request.getParameter(AppContextConstant.TRAIN_ID);
        String carriageType = request.getParameter(AppContextConstant.CARRIAGE_TYPE);
        String countSeats = request.getParameter(AppContextConstant.COUNT_SEATS);

        if (StringUtils.isNoneBlank(carriageId, carriageNumber, trainId, carriageType, countSeats)) {
            String trainNotSelected = trainId.equals(AppContextConstant.TRAIN_NOT_SELECTED) ? null : trainId;
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
            forward = Path.COMMAND_INFO_CARRIAGES;
        } else if (StringUtils.isNoneBlank(trainId, carriageType, countSeats)) {
            String trainNotSelected = trainId.equals(AppContextConstant.TRAIN_NOT_SELECTED) ? null : trainId;
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
            forward = Path.COMMAND_INFO_CARRIAGES;
        } else if (StringUtils.isNoneBlank(carriageId)) {
            int carId = Integer.parseInt(carriageId);
            Carriage car = carriageService.getCarById(carId);
            List<Train> trainList = trainService.getAllTrainList();
            List<CarriageType> carTypeList = new ArrayList<>(EnumSet.allOf(CarriageType.class));
            request.setAttribute(AppContextConstant.CARRIAGE, car);
            request.setAttribute(AppContextConstant.TRAIN_LIST, trainList);
            request.setAttribute(AppContextConstant.CARRIAGE_TYPE_LIST, carTypeList);
            int countSeat = seatService.getCountSeat(carId);
            request.setAttribute(AppContextConstant.COUNT_SEATS, countSeat);
        } else {
            List<Train> trainList = trainService.getAllTrainList();
            request.setAttribute(AppContextConstant.TRAIN_LIST, trainList);
            List<CarriageType> carTypeList = new ArrayList<>(EnumSet.allOf(CarriageType.class));
            request.setAttribute(AppContextConstant.CARRIAGE_TYPE_LIST, carTypeList);
        }
        request.setAttribute(AppContextConstant.FILTER_TRAIN, request.getParameter(AppContextConstant.FILTER_TRAIN));
        request.setAttribute(AppContextConstant.FILTER_TYPE_CARRIAGE, request.getParameter(AppContextConstant.FILTER_TYPE_CARRIAGE));
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
