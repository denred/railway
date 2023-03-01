package com.epam.redkin.railway.model.service.impl;

import com.epam.redkin.railway.model.dto.BookingDTO;
import com.epam.redkin.railway.model.dto.ReservationDTO;
import com.epam.redkin.railway.model.entity.CarriageType;
import com.epam.redkin.railway.model.entity.Order;
import com.epam.redkin.railway.model.entity.OrderStatus;
import com.epam.redkin.railway.model.entity.Seat;
import com.epam.redkin.railway.model.exception.DataBaseException;
import com.epam.redkin.railway.model.exception.IncorrectDataException;
import com.epam.redkin.railway.model.exception.ServiceException;
import com.epam.redkin.railway.model.repository.OrderRepository;
import com.epam.redkin.railway.model.repository.SeatRepository;
import com.epam.redkin.railway.model.service.OrderService;
import com.epam.redkin.railway.model.service.SeatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;


public class OrderServiceImpl implements OrderService {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceImpl.class);

    private final OrderRepository orderRepository;
    private final SeatRepository seatRepository;
    private final SeatService seatService;
    private final DataSource dataSource;

    public OrderServiceImpl(OrderRepository orderRepository, SeatService seatService, SeatRepository seatRepository, DataSource dataSource) {
        this.orderRepository = orderRepository;
        this.seatRepository = seatRepository;
        this.seatService = seatService;
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
    public void cancelOrder(int orderId) {
        Order order = getOrderById(orderId);
        LocalDateTime now = LocalDateTime.now();
        order.setOrderStatus(OrderStatus.CANCELED);
        validateDate(order, now);
        String seatNumber = order.getSeatNumber();
        List<Seat> seatsByIdBatch = seatRepository.getListSeatsByIdBatch(List.of(seatNumber.split(" ")));
        seatsByIdBatch.forEach(seat -> seatRepository.clearSeat(seat.getId()));
        try {
            orderRepository.updateOrderStatus(order.getId(), order.getOrderStatus());
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
    public List<Order> getOrderByUserId(int userId) {
        return orderRepository.getOrderByUserId(userId);
    }

    @Override
    public boolean updateOrderStatus(int orderId, OrderStatus status) {
        if (status == OrderStatus.DECLINED || status == OrderStatus.CANCELED) {
            Order order = orderRepository.getById(orderId);
            String seatNumber = order.getSeatNumber();
            List<String> seatsId = seatService.getSeatsIdFromOrder(seatNumber);
            List<Seat> seatsByIdBatch = seatRepository.getListSeatsByIdBatch(seatsId);
            seatsByIdBatch.forEach(seat -> seatRepository.clearSeat(seat.getId()));
        }
        try {
            return orderRepository.updateOrderStatus(orderId, status);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Order getOrderById(int orderId) {
        return orderRepository.getById(orderId);
    }

    @Override
    public List<Order> getAllOrderList() {
        return orderRepository.getOrders();
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
    public Double getPriceOfSuccessfulOrders(int userId) {
        return orderRepository.getPriceOfSuccessfulOrders(userId);
    }

    @Override
    public List<Order> getOrderListByCurrentRecordAndRecordsPerPage(int currentPage, int recordsPerPage) {
        List<Order> allRecords = orderRepository.getOrders();
        return allRecords.subList(currentPage, Math.min(recordsPerPage, allRecords.size()));
    }

    @Override
    public int getOrderListSize() {
        return orderRepository.getOrders().size();
    }

    @Override
    public List<Order> getOrderListByUserIdAndByCurrentRecordAndRecordsPerPage(String userId, int currentPage, int recordsPerPage) {
        List<Order> allRecords = orderRepository.getOrderByUserId(Integer.parseInt(userId));
        return allRecords.subList(currentPage, Math.min(recordsPerPage, allRecords.size()));
    }

    @Override
    public int getOrderListSizeByUserId(String userId) {
        return orderRepository.getOrderByUserId(Integer.parseInt(userId)).size();
    }

    @Override
    public void addReservation(List<ReservationDTO> reservations) {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);

            for (ReservationDTO reservationDTO : reservations) {
                orderRepository.addReservation(connection, reservationDTO);
            }

            connection.commit();
        } catch (SQLException e) {
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
                }
            } catch (SQLException ex) {
                LOGGER.error("Failed to close database connection: " + ex.getMessage());
            }
        }
    }

    @Override
    public int saveBooking(BookingDTO bookingDTO) {
        return orderRepository.saveBooking(bookingDTO);
    }

    @Override
    public void saveBookingSeat(int bookingId, String seatId) {
        orderRepository.saveBookingSeat(bookingId, seatId);
    }


}
