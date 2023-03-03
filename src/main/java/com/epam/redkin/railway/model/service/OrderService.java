package com.epam.redkin.railway.model.service;

import com.epam.redkin.railway.model.entity.Order;
import com.epam.redkin.railway.model.entity.OrderStatus;
import com.epam.redkin.railway.model.entity.Seat;
import com.epam.redkin.railway.model.entity.User;


import java.util.List;

public interface OrderService {


    List<Order> getAllOrderList();


    Order getOrderById(int orderId);


    boolean updateOrderStatus(int orderId, OrderStatus status);


    void saveBooking(Order order, int routsId, List<Seat> seats);


    void cancelOrder(int orderId);


    Double getPrice(String carType, int countOfSeats);

    Double getSuccessfulOrdersPrice(String userId);

    List<Order> getOrderListByCurrentRecordAndRecordsPerPage(int currentPage, int recordsPerPage);

    int getOrderListSize();

    List<Order> getUserOrders(String userId, int currentPage, int recordsPerPage);


    int getCountUserOrders(String userId);

    void addReservation(String routeId, String stationIdD, String stationIdA, String trainId, String[] seatIds, String travelTime, Double price, String carriageId, User user);
}
