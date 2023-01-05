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
        CarService carService = new CarServiceImpl(carRepository, seatRepository);
        UserService userService = new UserServiceImpl(userRepository);
        RoutService routService = new RoutServiceImpl(routsRepository, seatService, carService);
        OrderService orderService = new OrderServiceImpl(orderRepository, seatService, seatRepository);
        StationService stationService = new StationServiceImpl(stationRepository);
        TrainService trainService = new TrainServiceImpl(trainRepository);
        RoutMappingService routMappingService = new RoutMappingServiceImpl(routMappingRepository);
        LogoutService logoutService = new LogoutServiceImpl();

        sce.getServletContext().setAttribute(AppContextConstant.USER_SERVICE, userService);
        sce.getServletContext().setAttribute(AppContextConstant.ORDER_SERVICE, orderService);
        sce.getServletContext().setAttribute(AppContextConstant.STATION_SERVICE, stationService);
        sce.getServletContext().setAttribute(AppContextConstant.ROUT_SERVICE, routService);
        sce.getServletContext().setAttribute(AppContextConstant.TRAIN_SERVICE, trainService);
        sce.getServletContext().setAttribute(AppContextConstant.CARS_SERVICE, carService);
        sce.getServletContext().setAttribute(AppContextConstant.ROUT_TO_STATION_MAPPING_SERVICE, routMappingService);
        sce.getServletContext().setAttribute(AppContextConstant.LOGOUT_SERVICE, logoutService);
        sce.getServletContext().setAttribute(AppContextConstant.SEAT_SERVICE, seatService);

    }
/*
    private DataSource getDataSource() {
        DataSource ds;
        try {
            Context context = new InitialContext();
            ds = (DataSource) context.lookup("java:comp/env/jdbc/railway");
        } catch (NamingException e) {
            String message = "Cannot initialize connection pool";
            LOGGER.error(message, e);
            throw new DataBaseException(message);
        }
        return ds;
    }*/

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
/*
    private void initLog4j(ServletContext sc) {
        System.out.println("Log4JInitServlet is initializing log4j");
        String log4jLocation = sc.getInitParameter("log4j-properties-location");
        System.out.println(log4jLocation);

        if (log4jLocation == null) {
            System.err.println("*** No log4j-properties-location init param, so initializing log4j with BasicConfigurator");
            BasicConfigurator.configure();
        } else {
            String log4jProp = sc.getRealPath("WEB-INF/log4j.properties");
            File properties = new File(log4jProp);
            if (properties.exists()) {
                System.out.println("Initializing log4j with: " + log4jProp);
                PropertyConfigurator.configure(log4jProp);
            } else {
                System.err.println("*** " + log4jProp + " file not found, so initializing log4j with BasicConfigurator");
                BasicConfigurator.configure();
            }
        }
    }*/
}
