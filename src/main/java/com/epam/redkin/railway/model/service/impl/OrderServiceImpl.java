package com.epam.redkin.railway.model.service.impl;

import com.epam.redkin.railway.model.dto.BookingDTO;
import com.epam.redkin.railway.model.dto.MappingInfoDTO;
import com.epam.redkin.railway.model.dto.ReservationDTO;
import com.epam.redkin.railway.model.entity.*;
import com.epam.redkin.railway.model.exception.DataBaseException;
import com.epam.redkin.railway.model.exception.IncorrectDataException;
import com.epam.redkin.railway.model.exception.ServiceException;
import com.epam.redkin.railway.model.repository.OrderRepository;
import com.epam.redkin.railway.model.repository.SeatRepository;
import com.epam.redkin.railway.model.service.OrderService;
import com.epam.redkin.railway.model.service.RouteMappingService;
import com.epam.redkin.railway.model.service.SeatService;
import com.epam.redkin.railway.model.service.StationService;
import com.epam.redkin.railway.util.uuid.ReservationIDGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class OrderServiceImpl implements OrderService {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceImpl.class);


    private final OrderRepository orderRepository;
    private final SeatRepository seatRepository;
    private final SeatService seatService;
    private final RouteMappingService routeMappingService;
    private final StationService stationService;
    private final DataSource dataSource;

    public OrderServiceImpl(OrderRepository orderRepository, SeatService seatService, SeatRepository seatRepository, RouteMappingService routeMappingService, StationService stationService, DataSource dataSource) {
        this.orderRepository = orderRepository;
        this.seatRepository = seatRepository;
        this.seatService = seatService;
        this.routeMappingService = routeMappingService;
        this.stationService = stationService;
        this.dataSource = dataSource;
    }

    @Override
    public void saveBooking(Order order, int routsId, List<Seat> seats) {
        order.setPrice(order.getCarriageType().getPrice() * order.getCountOfSeats());
        seats.forEach(seat -> seatRepository.reservedSeat(seat.getId()));
        try {
            orderRepository.create(order);
        } catch (DataBaseException e) {
            throw new ServiceException("Failed to store order into database", e.getMessage());
        }
    }

    @Override
    public void cancelOrder(String orderId) {
        Order order = getOrderById(orderId);
        LocalDateTime now = LocalDateTime.now();
        order.setOrderStatus(OrderStatus.CANCELED);
        validateDate(order, now);
        String seatNumber = order.getSeatNumber();
        List<Seat> seatsByIdBatch = seatRepository.getListSeatsByIdBatch(List.of(seatNumber.split(" ")));
        seatsByIdBatch.forEach(seat -> seatRepository.clearSeat(seat.getId()));
        try {
            orderRepository.updateOrderStatus(order.getUuid(), order.getOrderStatus());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void validateDate(Order order, LocalDateTime now) {
        if (now.isAfter(order.getArrivalDate()) || now.isEqual(order.getArrivalDate())) {
            IncorrectDataException e = new IncorrectDataException("Can`t cancel the order because the cancellation " +
                    "period has been reached");
            LOGGER.error(e.getMessage());
            throw e;
        }
    }

    @Override
    public boolean updateOrderStatus(String orderUuid, OrderStatus status) {
        if (status == OrderStatus.DECLINED || status == OrderStatus.CANCELED) {
            Order order = orderRepository.getOrderByUUID(orderUuid);
            String seatNumber = order.getSeatNumber();
            List<String> seatsId = seatService.getSeatsIdFromOrder(seatNumber);
            List<Seat> seatsByIdBatch = seatRepository.getListSeatsByIdBatch(seatsId);
            seatsByIdBatch.forEach(seat -> seatRepository.clearSeat(seat.getId()));
        }
        try {
            return orderRepository.updateOrderStatus(orderUuid, status);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Order getOrderById(String orderId) {
        return orderRepository.getOrderByUUID(orderId);
    }


    @Override
    public Double getPrice(String carriageType, int countOfSeats) {
        LOGGER.info("Started --> getPrice(String carriageType, int countOfSeats) --> " +
                "carriageType: " + carriageType + " countOfSeats: " + countOfSeats);
        double price = CarriageType.valueOf(carriageType).getPrice() * countOfSeats;
        LOGGER.debug("Price= " + price);
        return price;
    }

    @Override
    public Double getSuccessfulOrdersPrice(String userId) {
        return orderRepository.getSuccessfulOrdersPrice(Integer.parseInt(userId));
    }

    @Override
    public List<Order> getOrders(int currentPage, int recordsPerPage) {
        return orderRepository.getOrders(currentPage, recordsPerPage);
    }

    @Override
    public int getOrdersCount() {
        return orderRepository.getCountOrders();
    }

    @Override
    public List<Order> getUserOrders(String userId, int currentPage, int recordsPerPage) {
        return orderRepository.getOrderByUserId(Integer.parseInt(userId), currentPage, recordsPerPage);
    }

    @Override
    public int getCountUserOrders(String userId) {
        return orderRepository.getCountUserOrders(Integer.parseInt(userId));
    }

    @Override
    public String addReservation(String routeId, String stationIdD, String stationIdA, String trainId, String seatIdsString, String travelTime, Double price, String carriageId, User user, String balancePayment) {
        LOGGER.info("Started add reservation routeId={}, stationIdD={}, stationIdA={}, trainId={}, seatIds={}, balancePayment={}",
                routeId, stationIdD, stationIdA, trainId, seatIdsString, balancePayment);
        String bookingId;
        String[] seatIds = seatIdsString.replaceAll("[\\[\\]\\s]", "").split(",");
        List<ReservationDTO> reservations = createReservationDTO(routeId, stationIdD, stationIdA, trainId, seatIds);

        BookingDTO bookingDTO = createBookingDTO(routeId, stationIdD, stationIdA, trainId, travelTime, price, carriageId, user);

        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);

            for (ReservationDTO reservationDTO : reservations) {
                orderRepository.addReservation(connection, reservationDTO);
                LOGGER.info("Reservation added: " + reservationDTO.toString());
            }

            bookingId = orderRepository.saveBooking(connection, bookingDTO);
            LOGGER.info("Booking saved: " + bookingDTO.toString());

            for (String seatId : seatIds) {
                orderRepository.saveBookingSeat(connection, bookingId, seatId);
                LOGGER.info("Seat bookingId={}, seatId={} booked", bookingId, seatId);
            }

            if (balancePayment != null && balancePayment.equals("on")) {
                if (price <= user.getBalance()) {
                    orderRepository.changeUserBalance(connection, user.getUserId(), user.getBalance() - price);
                } else {
                    connection.rollback();
                    LOGGER.error("Insufficient balance price={}, balance={}", price, user.getBalance());
                    throw new ServiceException("Insufficient balance");
                }
            }
            connection.commit();
            LOGGER.info("All reservations added successfully.");
        } catch (SQLException | ServiceException e) {
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException ex) {
                LOGGER.error("Failed to rollback transaction: " + ex.getMessage());
                throw new DataBaseException("Failed to rollback transaction.", ex);
            }

            LOGGER.error("Failed to add reservations: " + e.getMessage());
            throw new DataBaseException("Failed to add reservations.", e);
        } finally {
            try {
                if (connection != null) {
                    connection.setAutoCommit(true);
                    connection.close();
                    LOGGER.info("Database connection closed.");
                }
            } catch (SQLException ex) {
                LOGGER.error("Failed to close database connection: " + ex.getMessage());
            }
        }
        return bookingId;
    }

    private BookingDTO createBookingDTO(String routeId, String stationIdD, String stationIdA, String trainId, String travelTime, Double price, String carriageId, User user) {
        Station dispatchStation = stationService.getStationById(Integer.parseInt(stationIdD));
        Station arrivalStation = stationService.getStationById(Integer.parseInt(stationIdA));
        MappingInfoDTO arrivalMapping = routeMappingService.getMappingInfo(Integer.parseInt(routeId), arrivalStation.getId());
        MappingInfoDTO dispatchMapping = routeMappingService.getMappingInfo(Integer.parseInt(routeId), dispatchStation.getId());

        return BookingDTO.builder()
                .withBookingDate(LocalDateTime.now())
                .withDispatchDate(dispatchMapping.getStationDispatchData())
                .withArrivalDate(arrivalMapping.getStationArrivalDate())
                .withTravelTime(travelTime)
                .withPrice(price)
                .withBookingStatus(OrderStatus.ACCEPTED)
                .withUserId(user.getUserId())
                .withRouteId(Integer.parseInt(routeId))
                .withTrainId(Integer.parseInt(trainId))
                .withDispatchStationId(Integer.parseInt(stationIdD))
                .withArrivalStationId(Integer.parseInt(stationIdA))
                .withCarriageId(Integer.parseInt(carriageId))
                .build();
    }

    private List<ReservationDTO> createReservationDTO(String routeId, String stationIdD, String stationIdA, String trainId, String[] seatIds) {
        List<ReservationDTO> reservations = new ArrayList<>();
        List<MappingInfoDTO> routeStations = routeMappingService.getRouteStations(Integer.parseInt(routeId), Integer.parseInt(stationIdD), Integer.parseInt(stationIdA));
        for (MappingInfoDTO routeStation : routeStations) {
            for (String seatId : seatIds) {
                ReservationDTO reservation = ReservationDTO.builder()
                        .uuid(ReservationIDGenerator.generateReservationID())
                        .status("reserved")
                        .stationId(Integer.parseInt(routeStation.getStationId()))
                        .seatId(Integer.parseInt(seatId))
                        .trainId(Integer.parseInt(trainId))
                        .routeId(Integer.parseInt(routeStation.getRoutsId()))
                        .sequenceNumber(routeStation.getOrder())
                        .build();
                reservations.add(reservation);
            }
        }
        return reservations;
    }
}
