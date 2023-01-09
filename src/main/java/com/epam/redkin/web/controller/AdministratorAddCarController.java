package com.epam.redkin.web.controller;


import com.epam.redkin.model.dto.CarriageDTO;
import com.epam.redkin.model.entity.Carriage;
import com.epam.redkin.model.entity.CarriageType;
import com.epam.redkin.model.entity.Train;
import com.epam.redkin.model.exception.IncorrectDataException;
import com.epam.redkin.service.CarriageService;
import com.epam.redkin.service.TrainService;
import com.epam.redkin.util.constants.AppContextConstant;
import com.epam.redkin.validator.CarValidator;
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

@WebServlet("/administrator_add_car")
public class AdministratorAddCarController extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(AdministratorAddCarController.class);
    private CarriageService carriageService;
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
        CarriageDTO carriageDTO = new CarriageDTO();

        String trainId = request.getParameter("train_id");
        String trainNotSelected = trainId.equals("TRAIN_NOT_SELECTED") ? null : trainId;
        carriageDTO.setTrainId(Integer.parseInt(trainNotSelected));
        Train train = trainService.getTrainById(carriageDTO.getTrainId());
        List<Carriage> trainCarriages = carriageService.getCarByTrainId(train.getId());
        String carNumber = request.getParameter("car_number");
        carriageDTO.setCarNumber(carNumber);

        try {
            carriageDTO.setCarriageType(CarriageType.valueOf(request.getParameter("car_type")));
            carriageDTO.setSeats(Integer.valueOf(request.getParameter("seats")));
        } catch (IllegalArgumentException e) {
            LOGGER.error("Incorrect data entered");
            throw new IncorrectDataException("Incorrect data entered", e);
        }
        carValidator.isValidCar(carriageDTO);
        carriageService.addCarriage(carriageDTO);
        response.sendRedirect("administrator_info_carraiage");
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
        carriageService = (CarriageService) config.getServletContext().getAttribute(AppContextConstant.CARS_SERVICE);
        LOGGER.trace("administrator_add_car Servlet init");

    }
}

