package com.epam.redkin.railway.appcontext;

import com.epam.redkin.railway.model.connectionpool.ConnectionPools;
import com.epam.redkin.railway.model.repository.*;
import com.epam.redkin.railway.model.repository.impl.*;
import com.epam.redkin.railway.model.service.impl.*;
import com.epam.redkin.railway.model.service.*;
import com.epam.redkin.railway.model.validator.TrainValidator;

public class AppContext {
    private static final AppContext appContext = new AppContext();

    private final UserRepository userRepository = new UserRepositoryImpl(ConnectionPools.getProcessing());
    private final OrderRepository orderRepository = new OrderRepositoryImpl(ConnectionPools.getProcessing());
    private final StationRepository stationRepository = new StationRepositoryImpl(ConnectionPools.getProcessing());
    private final RouteRepository routsRepository = new RouteRepositoryImpl(ConnectionPools.getProcessing());
    private final TrainRepository trainRepository = new TrainRepositoryImpl(ConnectionPools.getProcessing());
    private final CarriageRepository carRepository = new CarriageRepositoryImpl(ConnectionPools.getProcessing());
    private final RoutePointRepository routePointRepository = new RoutePointRepositoryImpl(ConnectionPools.getProcessing());
    private final SeatRepository seatRepository = new SeatRepositoryImpl(ConnectionPools.getProcessing());

    private final SeatService seatService = new SeatServiceImpl(seatRepository);
    private final CarriageService carriageService = new CarriageServiceImpl(carRepository, seatRepository);
    private final UserService userService = new UserServiceImpl(userRepository);
    private final RouteService routeService = new RouteServiceImpl(routsRepository, seatService, carriageService);
    private final OrderService orderService = new OrderServiceImpl(orderRepository, seatService, seatRepository, ConnectionPools.getProcessing());
    private final StationService stationService = new StationServiceImpl(stationRepository);
    private final TrainService trainService = new TrainServiceImpl(trainRepository);
    private final RouteMappingService routeMappingService = new RouteMappingServiceImpl(routePointRepository);
    private final LogoutService logoutService = new LogoutServiceImpl();
    private final SearchService searchService = new SearchServiceImpl();
    private final PaginationService paginationService = new PaginationServiceImpl();
    private final TrainValidator trainValidator = new TrainValidator();


    public static AppContext getInstance() {
        return appContext;
    }

    public UserService getUserService() {
        return userService;
    }

    public RouteService getRouteService() {
        return routeService;
    }

    public SeatService getSeatService() {
        return seatService;
    }

    public OrderService getOrderService() {
        return orderService;
    }

    public RouteMappingService getRouteMappingService() {
        return routeMappingService;
    }

    public CarriageService getCarriageService() {
        return carriageService;
    }

    public TrainService getTrainService() {
        return trainService;
    }

    public StationService getStationService() {
        return stationService;
    }

    public LogoutService getLogoutService() {
        return logoutService;
    }

    public PaginationService getPaginationService() {
        return paginationService;
    }

    public SearchService getSearchService() {
        return searchService;
    }

    public TrainValidator getTrainValidator() {
        return trainValidator;
    }

}
