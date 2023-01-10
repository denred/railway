package com.epam.redkin.web.listener;


import com.epam.redkin.model.repository.*;
import com.epam.redkin.model.repository.impl.*;
import com.epam.redkin.service.*;
import com.epam.redkin.service.impl.*;
import com.epam.redkin.util.constants.AppContextConstant;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebListener
public class ApplicationContextListener implements ServletContextListener {
    private final static Logger LOGGER = LoggerFactory.getLogger(ServletContextListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        UserRepository userRepository = new UserRepositoryImpl();
        OrderRepository orderRepository = new OrderRepositoryImpl();
        StationRepository stationRepository = new StationRepositoryImpl();
        RouteRepository routsRepository = new RouteRepositoryImpl();
        TrainRepository trainRepository = new TrainRepositoryImpl();
        CarriageRepository carRepository = new CarriageRepositoryImpl();
        RoutePointRepository routMappingRepository = new RoutePointRepositoryImpl();
        SeatRepository seatRepository = new SeatRepositoryImpl();


        SeatService seatService = new SeatServiceImpl(seatRepository);
        CarriageService carriageService = new CarriageServiceImpl(carRepository, seatRepository);
        UserService userService = new UserServiceImpl(userRepository);
        RouteService routeService = new RouteServiceImpl(routsRepository, seatService, carriageService);
        OrderService orderService = new OrderServiceImpl(orderRepository, seatService, seatRepository);
        StationService stationService = new StationServiceImpl(stationRepository);
        TrainService trainService = new TrainServiceImpl(trainRepository);
        RouteMappingService routeMappingService = new RouteMappingServiceImpl(routMappingRepository);
        LogoutService logoutService = new LogoutServiceImpl();

        sce.getServletContext().setAttribute(AppContextConstant.USER_SERVICE, userService);
        sce.getServletContext().setAttribute(AppContextConstant.ORDER_SERVICE, orderService);
        sce.getServletContext().setAttribute(AppContextConstant.STATION_SERVICE, stationService);
        sce.getServletContext().setAttribute(AppContextConstant.ROUT_SERVICE, routeService);
        sce.getServletContext().setAttribute(AppContextConstant.TRAIN_SERVICE, trainService);
        sce.getServletContext().setAttribute(AppContextConstant.CARS_SERVICE, carriageService);
        sce.getServletContext().setAttribute(AppContextConstant.ROUT_TO_STATION_MAPPING_SERVICE, routeMappingService);
        sce.getServletContext().setAttribute(AppContextConstant.LOGOUT_SERVICE, logoutService);
        sce.getServletContext().setAttribute(AppContextConstant.SEAT_SERVICE, seatService);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}
