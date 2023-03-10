package com.epam.redkin.railway.model.repository;

import com.epam.redkin.railway.model.dto.BookingDTO;
import com.epam.redkin.railway.model.dto.ReservationDTO;
import com.epam.redkin.railway.model.entity.Order;
import com.epam.redkin.railway.model.entity.OrderStatus;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface OrderRepository{
    void create(Order order);
    Order getOrderByUUID(String uuid);
    List<Order> getOrders(int currentPage, int recordsPerPage);
    boolean updateOrderStatus(String orderUuid, OrderStatus status) throws SQLException;
    List<Order> getOrderByUserId(int userId, int currentPage, int recordsPerPage);
    Double getSuccessfulOrdersPrice(int orderId);

    void addReservation(Connection connection, ReservationDTO reservationDTO);

    String saveBooking(Connection connection, BookingDTO bookingDTO);

    void saveBookingSeat(Connection connection, String bookingId, String seatId);

    int getCountUserOrders(int userId);

    int getCountOrders();

    void changeUserBalance(Connection connection, int userId, double newBalance);
}
