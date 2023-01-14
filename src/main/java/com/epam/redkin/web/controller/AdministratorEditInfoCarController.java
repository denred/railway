package com.epam.redkin.web.controller;

import com.epam.redkin.model.dto.CarriageDTO;
import com.epam.redkin.model.entity.Carriage;
import com.epam.redkin.model.entity.CarriageType;
import com.epam.redkin.model.entity.Train;
import com.epam.redkin.model.exception.IncorrectDataException;
import com.epam.redkin.model.service.CarriageService;
import com.epam.redkin.model.service.SeatService;
import com.epam.redkin.model.service.TrainService;
import com.epam.redkin.util.constants.AppContextConstant;
import com.epam.redkin.model.validator.CarriageValidator;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

@WebServlet("/administrator_edit_info_car")
public class AdministratorEditInfoCarController extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(AdministratorEditInfoCarController.class);
    private CarriageService carriageService;
    private TrainService trainService;
    private SeatService seatService;

    public static boolean containsCarWithCarNumber(final List<Carriage> array, final String carNumber) {
        return array.stream().anyMatch(car -> car.getNumber().equals(carNumber));
    }

    public static boolean containsCarWithCarId(final List<Carriage> array, final int carId) {
        return array.stream().anyMatch(car -> car.getCarriageId() == carId);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        CarriageValidator carriageValidator = new CarriageValidator();
        CarriageDTO carriageDTO = new CarriageDTO();
        String carId = request.getParameter("car_id");
        carriageDTO.setCarId(Integer.parseInt(carId));
        String carNumber = request.getParameter("car_number");
        String trainId = request.getParameter("train_id");
        String trainNotSelected = trainId.equals("TRAIN_NOT_SELECTED") ? null : trainId;
        carriageDTO.setTrainId(Integer.parseInt(trainNotSelected));
        Train train = trainService.getTrainById(Integer.parseInt(trainId));
        List<Carriage> carByTrainId = carriageService.getCarByTrainId(train.getId());
        if (train != null || containsCarWithCarId(carByTrainId, Integer.parseInt(carId))
                && containsCarWithCarNumber(carByTrainId, carNumber) && trainId.equals(train.getId())) {
            carriageDTO.setCarNumber(carNumber);
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
        response.sendRedirect("administrator_info_carriage");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int carId = Integer.parseInt(request.getParameter("car_id"));
        Carriage car = carriageService.getCarById(carId);
        request.setAttribute("current_car", car);
        List<Train> trainList = trainService.getAllTrainList();
        request.setAttribute("trainList", trainList);
        List<CarriageType> carTypeList = new ArrayList<>(EnumSet.allOf(CarriageType.class));
        request.setAttribute("carTypeList", carTypeList);
        int countSeat = seatService.getCountSeat(carId);
        request.setAttribute("countSeat", countSeat);

        request.getRequestDispatcher("WEB-INF/jsp/carriageSet.jsp").forward(request, response);
    }

    @Override
    public void init(ServletConfig config) {
        trainService = (TrainService) config.getServletContext().getAttribute(AppContextConstant.TRAIN_SERVICE);
        carriageService = (CarriageService) config.getServletContext().getAttribute(AppContextConstant.CARS_SERVICE);
        seatService = (SeatService) config.getServletContext().getAttribute(AppContextConstant.SEAT_SERVICE);
        LOGGER.trace("administrator_edit_info_car Servlet init");

    }
}


