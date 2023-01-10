package com.epam.redkin.web.controller;

import com.epam.redkin.model.dto.MappingInfoDTO;
import com.epam.redkin.model.dto.RouteInfoDTO;
import com.epam.redkin.model.entity.*;
import com.epam.redkin.model.exception.IncorrectDataException;
import com.epam.redkin.service.*;
import com.epam.redkin.util.constants.AppContextConstant;
import com.epam.redkin.validator.OrderValidator;
import com.epam.redkin.validator.SeatValidator;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.DateTimeException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;

@WebServlet("/confirm_order")
public class ConfirmOrderController extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConfirmOrderController.class);
    private OrderService orderService;
    private StationService stationService;
    private TrainService trainService;
    private RoutMappingService routMappingService;
    private CarriageService carriageService;
    private UserService userService;
    private SeatService seatService;
    private RouteService routeService;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        OrderValidator orderValidator = new OrderValidator();
        Order order = new Order();
        User user = (User) request.getSession().getAttribute(AppContextConstant.SESSION_USER);

        String routsId = request.getParameter("routs_id");

        String trainId = request.getParameter("train_id");

        String stationIdA = request.getParameter("arrival_station_id");
        String stationIdD = request.getParameter("departure_station_id");

        String carId = request.getParameter("car_id");


        Carriage car = carriageService.getCarById(Integer.parseInt(carId));


        Train train = trainService.getTrainById(Integer.parseInt(trainId));


        Station dispatchStation = stationService.getStationById(Integer.parseInt(stationIdA));


        Station arrivalStation = stationService.getStationById(Integer.parseInt(stationIdD));



        MappingInfoDTO arrivalMapping = routMappingService.getMappingInfo(Integer.parseInt(routsId), arrivalStation.getId());



        MappingInfoDTO dispatchMapping = routMappingService.getMappingInfo(Integer.parseInt(routsId), dispatchStation.getId());



        HttpSession session = request.getSession();
        Object locale = session.getAttribute(AppContextConstant.LOCALE);
        try {
            order.setCarrType(CarriageType.valueOf(request.getParameter("car_type")));
            order.setCountOfSeats(Integer.parseInt(request.getParameter("count_of_seats")));

            if (locale == AppContextConstant.LOCALE_EN) {
                Duration duration = Duration.between(arrivalMapping.getStationDispatchData(), dispatchMapping.getStationArrivalDate());
                order.setTravelTime(String.format("Days: %s Hours: %s Minutes: %s", duration.toDays(),
                        duration.toHours() % 24, duration.toMinutes() % 60));
            }
            if (locale == AppContextConstant.LOCALE_UA) {
                Duration duration = Duration.between(arrivalMapping.getStationDispatchData(), dispatchMapping.getStationArrivalDate());
                order.setTravelTime(String.format("Дней: %s Часов: %s Минут: %s", duration.toDays(),
                        duration.toHours() % 24, duration.toMinutes() % 60));
            }

        } catch (IllegalArgumentException | ArithmeticException | DateTimeException e) {
            LOGGER.error(e.getMessage());
            throw new IncorrectDataException("Incorrect data entered", e);
        }


        order.setRouteId(Integer.parseInt(routsId));
        order.setArrivalDate(arrivalMapping.getStationDispatchData());
        order.setDispatchDate(dispatchMapping.getStationArrivalDate());
        order.setUser(user);
        order.setTrainNumber(train.getNumber());
        order.setCarriageNumber(car.getNumber());
        order.setOrderDate(LocalDateTime.now());
        order.setOrderStatus(OrderStatus.PROCESSING);
        order.setArrivalStation(dispatchStation.getStation());
        order.setDispatchStation(arrivalStation.getStation());

        String seatId = Arrays.toString(request.getParameterValues("seat_id"));


        List<String> seatIdList = seatService.getSeatsId(seatId);


        List<Seat> seats = seatService.getSeatsByIdBatch(seatIdList);


        StringBuilder sb = new StringBuilder();
        String number = "";
        for (int i = 0; i <= seats.size() - 1; i++) {
            number = sb.append(seats.get(i).getSeatNumber()).append(" ").toString();
        }



        order.setSeatNumber(number);
        sb = new StringBuilder();
        String id = "";
        for (int i = 0; i <= seats.size() - 1; i++) {
            id = sb.append(seats.get(i).getId()).append(" ").toString();
        }
        order.setSeatsId(id);
        orderValidator.isValidOrder(order);

        orderService.addOrder(order, Integer.parseInt(routsId), seats);
        int userId = user.getUserId();
        response.sendRedirect("user_account?user_id=" + userId);
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SeatValidator seatValidator = new SeatValidator();
        String departureStation = request.getParameter("departure_station");
        String arrivalStation = request.getParameter("arrival_station");
        String departureStationId = request.getParameter("departure_station_id");
        String arrivalStationId = request.getParameter("arrival_station_id");
        String carType = request.getParameter("car_type");
        String trainId = request.getParameter("train_id");
        String carId = request.getParameter("car_id");
        String countOfSeats = request.getParameter("count_of_seats");
        String userId = request.getParameter("user_id");
        String station1 = request.getParameter("station1");
        String station2 = request.getParameter("station2");
        String travelTime = request.getParameter("travel_time");

        request.setAttribute("station1", station1);
        request.setAttribute("station2", station2);
        request.setAttribute("travel_time", travelTime);
        LocalDateTime departureDate;
        try {
            departureDate = LocalDateTime.parse(request.getParameter("departure_date"));
        } catch (DateTimeParseException e) {
            LOGGER.error("Incorrect data entered");
            throw new IncorrectDataException("Incorrect data entered", e);
        }
        String[] numbers = request.getParameterValues("seats_number");
        List<String> seatsNumber = Arrays.asList(request.getParameterValues("seats_number"));


        String routsId = request.getParameter("routs_id");
        RouteInfoDTO routById = routeService.getRoutById(Integer.parseInt(routsId));


        String routName = routById.getRoutName();
        Carriage car = carriageService.getCarById(Integer.parseInt(carId));
        String carNumber = car.getNumber();
        Double price = orderService.getPrice(carType, Integer.parseInt(countOfSeats));



        request.setAttribute("price", price);
        request.setAttribute("rout_name", routName);
        request.setAttribute("car_number", carNumber);
        request.setAttribute("departure_station", departureStation);
        request.setAttribute("arrival_station", arrivalStation);
        request.setAttribute("departure_date", departureDate);
        request.setAttribute("departure_station_id", departureStationId);
        request.setAttribute("arrival_station_id", arrivalStationId);
        request.setAttribute("routs_id", routsId);
        request.setAttribute("car_type", carType);
        request.setAttribute("train_id", trainId);
        request.setAttribute("user_id", userId);

        User user = userService.read(Integer.parseInt(userId));
        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        Train train = trainService.getTrainById(Integer.parseInt(trainId));
        String trainNumber = train.getNumber();




        request.setAttribute("train_number", trainNumber);
        request.setAttribute("first_name", firstName);
        request.setAttribute("last_name", lastName);
        request.setAttribute("car_number", carNumber);
        request.setAttribute("count_of_seats", countOfSeats);
        request.setAttribute("seats_number", seatsNumber);
        request.setAttribute("car_id", carId);
        List<Seat> seats = seatService.getSeatsByIdBatch(seatsNumber);



        request.setAttribute("seats", seats);
        request.setAttribute("seat_id", Arrays.deepToString(numbers));
        seatValidator.isValidSeat(seats, countOfSeats);

        request.getRequestDispatcher("WEB-INF/jsp/confirmOrder.jsp").forward(request, response);
    }


    public void init(ServletConfig config) {
        orderService = (OrderService) config.getServletContext().getAttribute(AppContextConstant.ORDER_SERVICE);
        stationService = (StationService) config.getServletContext().getAttribute((AppContextConstant.STATION_SERVICE));
        routMappingService = (RoutMappingService) config.getServletContext().getAttribute((AppContextConstant.ROUT_TO_STATION_MAPPING_SERVICE));
        trainService = (TrainService) config.getServletContext().getAttribute((AppContextConstant.TRAIN_SERVICE));
        carriageService = (CarriageService) config.getServletContext().getAttribute((AppContextConstant.CARS_SERVICE));
        userService = (UserService) config.getServletContext().getAttribute((AppContextConstant.USER_SERVICE));
        seatService = (SeatService) config.getServletContext().getAttribute((AppContextConstant.SEAT_SERVICE));
        routeService = (RouteService) config.getServletContext().getAttribute((AppContextConstant.ROUT_SERVICE));
        LOGGER.trace("confirm_order Servlet init");

    }
}