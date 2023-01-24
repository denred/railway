package com.epam.redkin.railway.model.repository;

import com.epam.redkin.railway.model.entity.Order;
import com.epam.redkin.railway.model.entity.OrderStatus;

import java.sql.SQLException;
import java.util.List;

public interface OrderRepository extends EntityDAO<Order> {
    List<Order> getOrders();
    boolean updateOrderStatus(int orderId, OrderStatus status) throws SQLException;
    List<Order> getOrderByUserId(int userId);
    Double getPriceOfSuccessfulOrders(int orderId);
}
