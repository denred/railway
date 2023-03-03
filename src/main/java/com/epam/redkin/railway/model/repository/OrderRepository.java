package com.epam.redkin.railway.model.repository;

import com.epam.redkin.railway.model.dto.BookingDTO;
import com.epam.redkin.railway.model.dto.ReservationDTO;
import com.epam.redkin.railway.model.entity.Order;
import com.epam.redkin.railway.model.entity.OrderStatus;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface OrderRepository extends EntityDAO<Order> {
    List<Order> getOrders();
    boolean updateOrderStatus(int orderId, OrderStatus status) throws SQLException;
    List<Order> getOrderByUserId(int userId, int currentPage, int recordsPerPage);
    Double getSuccessfulOrdersPrice(int orderId);

    void addReservation(Connection connection, ReservationDTO reservationDTO);

    int saveBooking(Connection connection, BookingDTO bookingDTO);

    void saveBookingSeat(Connection connection, int bookingId, String seatId);

    int getCountUserOrders(int userId);
}
