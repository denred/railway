package com.epam.redkin.railway.model.service;

import com.epam.redkin.railway.model.dto.BookingDTO;
import com.epam.redkin.railway.model.dto.ReservationDTO;
import com.epam.redkin.railway.model.entity.Order;
import com.epam.redkin.railway.model.entity.OrderStatus;
import com.epam.redkin.railway.model.entity.Seat;


import java.util.List;

public interface OrderService {


    List<Order> getAllOrderList();


    Order getOrderById(int orderId);


    boolean updateOrderStatus(int orderId, OrderStatus status);


    List<Order> getOrderByUserId(int userId);


    void saveBooking(Order order, int routsId, List<Seat> seats);


    void cancelOrder(int orderId);


    Double getPrice(String carType, int countOfSeats);

    Double getPriceOfSuccessfulOrders(int userId);

    List<Order> getOrderListByCurrentRecordAndRecordsPerPage(int currentPage, int recordsPerPage);

    int getOrderListSize();

    List<Order> getOrderListByUserIdAndByCurrentRecordAndRecordsPerPage(String userId, int currentPage, int recordsPerPage);


    int getOrderListSizeByUserId(String userId);

    void addReservation(List<ReservationDTO> reservations);

    int saveBooking(BookingDTO bookingDTO);

    void saveBookingSeat(int bookingId, String seatId);
}
