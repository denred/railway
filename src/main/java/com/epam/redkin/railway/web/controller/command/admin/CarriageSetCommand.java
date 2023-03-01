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
import com.epam.redkin.railway.web.controller.Path;
import com.epam.redkin.railway.web.controller.command.Command;
import com.epam.redkin.railway.appcontext.AppContext;
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

public class CarriageSetCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(CarriageSetCommand.class);

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("started");
        Router router = Router.redirect(Path.COMMAND_CARRIAGE_SET_PAGE);

        TrainService trainService = AppContext.getInstance().getTrainService();
        CarriageService carriageService = AppContext.getInstance().getCarriageService();
        SeatService seatService = AppContext.getInstance().getSeatService();
        HttpSession session = request.getSession();
        CarriageValidator carriageValidator = new CarriageValidator();
        CarriageDTO carriageDTO = CarriageDTO.builder().build();

        String carriageId = request.getParameter(CARRIAGE_ID);
        String carriageNumber = request.getParameter(CARRIAGE_NUMBER);
        String trainId = request.getParameter(TRAIN_ID);
        String carriageType = request.getParameter(CARRIAGE_TYPE);
        String countSeats = request.getParameter(COUNT_SEATS);

        if (StringUtils.isNoneBlank(carriageId, carriageNumber, trainId, carriageType, countSeats)) {
            carriageDTO.setCarId(Integer.parseInt(carriageId));
            carriageDTO.setTrainId(Integer.parseInt(trainId));
            Train train = trainService.getTrainById(Integer.parseInt(trainId));
            List<Carriage> carriageList = carriageService.getCarriageByTrainId(train.getId());

            if (checkCarriage(carriageList, carriageNumber, carriageId)) {
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
            carriageService.updateCarriage(carriageDTO);
            router.setRouteType(Router.RouteType.REDIRECT);
            router.setPagePath(Path.COMMAND_INFO_CARRIAGES);
        } else {
            Carriage carriage = carriageService.getCarriageById(Integer.parseInt(carriageId));
            List<Train> trainList = trainService.getTrainList();
            List<CarriageType> carriageTypeList = new ArrayList<>(EnumSet.allOf(CarriageType.class));
            int countSeat = seatService.getCountSeat(Integer.parseInt(carriageId));
            session.setAttribute(CARRIAGE, carriage);
            session.setAttribute(TRAIN_LIST, trainList);
            session.setAttribute(CARRIAGE_TYPE_LIST, carriageTypeList);
            session.setAttribute(COUNT_SEATS, countSeat);
            session.setAttribute(ADD_COMMAND, false);
        }
        LOGGER.info("done");
        return router;
    }

    public static boolean checkCarriage(final List<Carriage> array, final String carriageNumber, String carriageId) {
        CarriageService carriageService = AppContext.getInstance().getCarriageService();
        Carriage carriage = carriageService.getCarriageByNumber(carriageNumber);
        boolean state;
        if (carriage == null) {
            state = true;
        } else {
            state = carriage.getCarriageId() == Integer.parseInt(carriageId)
                    && (array.stream().noneMatch(car -> car.getNumber().equals(carriageNumber))
                    || carriage.getNumber().equals(carriageNumber));
        }
        return state;
    }
}
