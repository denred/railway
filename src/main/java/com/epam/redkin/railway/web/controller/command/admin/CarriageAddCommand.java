package com.epam.redkin.railway.web.controller.command.admin;

import com.epam.redkin.railway.appcontext.AppContext;
import com.epam.redkin.railway.model.dto.CarriageDTO;
import com.epam.redkin.railway.model.entity.CarriageType;
import com.epam.redkin.railway.model.entity.Train;
import com.epam.redkin.railway.model.exception.IncorrectDataException;
import com.epam.redkin.railway.model.service.CarriageService;
import com.epam.redkin.railway.model.service.TrainService;
import com.epam.redkin.railway.model.validator.CarriageValidator;
import com.epam.redkin.railway.util.constants.AppContextConstant;
import com.epam.redkin.railway.web.controller.Path;
import com.epam.redkin.railway.web.controller.command.Command;
import com.epam.redkin.railway.web.controller.command.Router;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import static com.epam.redkin.railway.util.constants.AppContextConstant.*;

public class CarriageAddCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(CarriageAddCommand.class);

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("started");
        Router router = new Router();
        router.setPagePath(Path.COMMAND_CARRIAGE_SET_PAGE);
        router.setRouteType(Router.RouteType.REDIRECT);

        TrainService trainService = AppContext.getInstance().getTrainService();
        CarriageService carriageService = AppContext.getInstance().getCarriageService();
        HttpSession session = request.getSession();
        CarriageValidator carriageValidator = new CarriageValidator();
        CarriageDTO carriageDTO = CarriageDTO.builder().build();

        String carriageNumber = request.getParameter(AppContextConstant.CARRIAGE_NUMBER);
        String trainId = request.getParameter(AppContextConstant.TRAIN_ID);
        String carriageType = request.getParameter(AppContextConstant.CARRIAGE_TYPE);
        String countSeats = request.getParameter(AppContextConstant.COUNT_SEATS);

        if (StringUtils.isNoneBlank(trainId, carriageType, countSeats, carriageNumber)) {
            carriageDTO.setTrainId(Integer.parseInt(trainId));
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
            router.setPagePath(Path.COMMAND_INFO_CARRIAGES);
            router.setRouteType(Router.RouteType.REDIRECT);
        } else {
            List<Train> trainList = trainService.getTrainList();
            List<CarriageType> carriageTypeList = new ArrayList<>(EnumSet.allOf(CarriageType.class));
            session.setAttribute(TRAIN_LIST, trainList);
            session.setAttribute(CARRIAGE_TYPE_LIST, carriageTypeList);
            session.setAttribute(ADD_COMMAND, true);
        }
        LOGGER.info("done");
        return router;
    }
}
