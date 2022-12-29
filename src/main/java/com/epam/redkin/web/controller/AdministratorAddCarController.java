package com.epam.redkin.web.controller;


import com.epam.redkin.model.dto.CarDto;
import com.epam.redkin.model.entity.Carriage;
import com.epam.redkin.model.entity.CarriageType;
import com.epam.redkin.model.entity.Train;
import com.epam.redkin.model.exception.IncorrectDataException;
import com.epam.redkin.service.CarService;
import com.epam.redkin.service.TrainService;
import com.epam.redkin.util.constants.AppContextConstant;
import com.epam.redkin.validator.CarValidator;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

@WebServlet("/administrator_add_car")
public class AdministratorAddCarController extends HttpServlet {
    //private static final Logger LOGGER = Logger.getLogger(AdministratorAddCarController.class);
    private CarService carService;
    private TrainService trainService;

    public static boolean containsCarWithCarNumber(final List<Carriage> array, final String carNumber) {

        boolean result = false;

        for (Carriage car : array) {
            if (car.getNumber().equals(carNumber)) {
                result = true;
                break;
            }
        }
        return result;
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        CarValidator carValidator = new CarValidator();
        CarDto carDto = new CarDto();
        String trainId = request.getParameter("train_id");
        String trainNotSelected = trainId.equals("TRAIN_NOT_SELECTED") ? null : trainId;
        carDto.setTrainId(Integer.parseInt(trainNotSelected));
        Train train = trainService.getTrainById(Integer.parseInt(trainId));
        List<Carriage> carByTrainId = carService.getCarByTrainId(train.getId());
        String carNumber = request.getParameter("car_number");

        /*if (train.getId() == null || !containsCarWithCarNumber(carByTrainId, carNumber) && trainId.equals(train.getTrainId())) {
            carDto.setCarNumber(carNumber);
        } else {
            LOGGER.error("Incorrect data entered");
            throw new IncorrectDataException("Incorrect data entered");
        }*/
        try {
            carDto.setCarType(CarriageType.valueOf(request.getParameter("car_type")));
            carDto.setSeats(Integer.valueOf(request.getParameter("seats")));
        } catch (IllegalArgumentException e) {
            //LOGGER.error("Incorrect data entered");
            throw new IncorrectDataException("Incorrect data entered", e);
        }
        carValidator.isValidCar(carDto);
        carService.addCar(carDto);
        response.sendRedirect("administrator_account");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Train> trainList = trainService.getAllTrainList();
        request.setAttribute("trainList", trainList);
        List<CarriageType> carTypeList = new ArrayList<>(EnumSet.allOf(CarriageType.class));
        request.setAttribute("carTypeList", carTypeList);

        request.getRequestDispatcher("WEB-INF/jsp/administratorAddCar.jsp").forward(request, response);
    }

    public void init(ServletConfig config) {
        trainService = (TrainService) config.getServletContext().getAttribute(AppContextConstant.TRAIN_SERVICE);
        carService = (CarService) config.getServletContext().getAttribute(AppContextConstant.CARS_SERVICE);
       // LOGGER.trace("administrator_add_car Servlet init");

    }
}

