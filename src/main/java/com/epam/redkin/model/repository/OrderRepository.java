package com.epam.redkin.model.repository;

import com.epam.redkin.model.entity.Order;
import com.epam.redkin.model.entity.OrderStatus;

import java.util.List;

public interface OrderRepository extends EntityDAO<Order> {
    List<Order> getAllOrders();
    boolean updateOrderStatus(int orderId, OrderStatus status);
    List<Order> getOrderByUserId(int userId);
    Double getPriceOfSuccessfulOrders(int orderId);
}
