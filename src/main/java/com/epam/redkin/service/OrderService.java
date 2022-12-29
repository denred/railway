package com.epam.redkin.service;

import com.epam.redkin.model.entity.Order;
import com.epam.redkin.model.entity.OrderStatus;
import com.epam.redkin.model.entity.Seat;


import java.util.List;

public interface OrderService {


    List<Order> getAllOrderList();


    Order getOrderById(int orderId);


    boolean updateOrderStatus(int orderId, OrderStatus status);


    List<Order> getOrderByUserId(int userId);


    void addOrder(Order order, int routsId, List<Seat> seats);


    void cancelOrder(int orderId);


    Double getPrice(String carType, int countOfSeats);

    Double getPriceOfSuccessfulOrders(int userId);
}
