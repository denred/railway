package com.epam.redkin.web.listener;

import com.epam.redkin.model.repository.*;
import com.epam.redkin.model.repository.impl.*;
import com.epam.redkin.service.*;
import com.epam.redkin.service.impl.*;

public class AppContext {
    private static final AppContext appContext = new AppContext();

    private final UserRepository userRepository = new UserRepositoryImpl();
    private final OrderRepository orderRepository = new OrderRepositoryImpl();
    private final StationRepository stationRepository = new StationRepositoryImpl();
    private final RouteRepository routsRepository = new RouteRepositoryImpl();
    private final TrainRepository trainRepository = new TrainRepositoryImpl();
    private final CarriageRepository carRepository = new CarriageRepositoryImpl();
    private final RoutePointRepository routePointRepository = new RoutePointRepositoryImpl();
    private final SeatRepository seatRepository = new SeatRepositoryImpl();

    private final SeatService seatService = new SeatServiceImpl(seatRepository);
    private final CarriageService carriageService = new CarriageServiceImpl(carRepository, seatRepository);
    private final UserService userService = new UserServiceImpl(userRepository);
    private final RouteService routeService = new RouteServiceImpl(routsRepository, seatService, carriageService);
    private final OrderService orderService = new OrderServiceImpl(orderRepository, seatService, seatRepository);
    private final StationService stationService = new StationServiceImpl(stationRepository);
    private final TrainService trainService = new TrainServiceImpl(trainRepository);
    private final RouteMappingService routeMappingService = new RouteMappingServiceImpl(routePointRepository);
    private final LogoutService logoutService = new LogoutServiceImpl();

    public static AppContext getInstance() {
        return appContext;
    }

    public UserService getUserService(){
        return userService;
    }

    public RouteService getRouteService(){
        return routeService;
    }

    public SeatService getSeatService(){
        return seatService;
    }

    public OrderService getOrderService(){return orderService;}

    public RouteMappingService getRouteMappingService(){return routeMappingService;}

}
