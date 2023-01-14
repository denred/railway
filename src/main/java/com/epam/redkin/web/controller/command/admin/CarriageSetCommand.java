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

import static com.epam.redkin.web.controller.Path.COMMAND_INFO_CARRIAGES;
import static com.epam.redkin.web.controller.Path.PAGE_ADMIN_SET_CARRIAGE;

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

        String carriageId = request.getParameter("car_id");
        String carriageNumber = request.getParameter("car_number");
        String trainId = request.getParameter("train_id");
        String carriageType = request.getParameter("car_type");
        String countSeats = request.getParameter("seats");

        if (StringUtils.isNoneBlank(carriageId, carriageNumber, trainId, carriageType, countSeats)) {
            String trainNotSelected = trainId.equals("TRAIN_NOT_SELECTED") ? null : trainId;
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
                carriageDTO.setCarriageType(CarriageType.valueOf(request.getParameter("car_type")));
                carriageDTO.setSeats(Integer.valueOf(request.getParameter("seats")));

            } catch (IllegalArgumentException e) {
                LOGGER.error("Incorrect data entered");
                throw new IncorrectDataException("Incorrect data entered", e);
            }
            carriageValidator.isValidCar(carriageDTO);
            carriageService.updateCar(carriageDTO);
            forward = COMMAND_INFO_CARRIAGES;
        } else if (StringUtils.isNoneBlank(trainId, carriageType, countSeats)) {
            String trainNotSelected = trainId.equals("TRAIN_NOT_SELECTED") ? null : trainId;
            assert trainNotSelected != null;
            carriageDTO.setTrainId(Integer.parseInt(trainNotSelected));
            Train train = trainService.getTrainById(carriageDTO.getTrainId());
            List<Carriage> trainCarriages = carriageService.getCarByTrainId(train.getId());
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
            request.setAttribute("current_car", car);
            request.setAttribute("trainList", trainList);
            request.setAttribute("carTypeList", carTypeList);
            int countSeat = seatService.getCountSeat(carId);
            request.setAttribute("countSeat", countSeat);
        } else {
            List<Train> trainList = trainService.getAllTrainList();
            request.setAttribute("trainList", trainList);
            List<CarriageType> carTypeList = new ArrayList<>(EnumSet.allOf(CarriageType.class));
            request.setAttribute("carTypeList", carTypeList);
        }
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
