package com.epam.redkin.model.repository.impl;

import com.epam.redkin.model.connectionpool.ConnectionPools;
import com.epam.redkin.model.entity.*;
import com.epam.redkin.model.exception.DataBaseException;
import com.epam.redkin.model.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderRepositoryImpl implements OrderRepository, Constants {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderRepositoryImpl.class);
    private final DataSource dataSource = ConnectionPools.getProcessing();

    @Override
    public int create(Order entity) {
        int key = -1;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(ADD_ORDER, Statement.RETURN_GENERATED_KEYS);
            statement.setObject(1, entity.getOrderDate());
            statement.setInt(2, entity.getRouteId());
            statement.setString(3, entity.getDispatchStation());
            statement.setObject(4, entity.getDispatchDate());
            statement.setString(5, entity.getArrivalStation());
            statement.setObject(6, entity.getArrivalDate());
            statement.setString(7, entity.getTravelTime());
            statement.setString(8, entity.getTrainNumber());
            statement.setString(9, entity.getCarriageNumber());
            statement.setString(10, entity.getCarrType().name());
            statement.setInt(11, entity.getCountOfSeats());
            statement.setString(12, entity.getSeatNumber());
            statement.setString(13, entity.getSeatsId());
            statement.setInt(14, entity.getUser().getUserId());
            statement.setDouble(15, entity.getPrice());
            statement.setString(16, entity.getOrderStatus().name());
            statement.executeUpdate();
            connection.commit();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                key = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            try {
                assert connection != null;
                connection.rollback();
            } catch (SQLException ex) {
                throw new DataBaseException("Cannot create order = " + entity);
            }
            LOGGER.error(e.getMessage());
            throw new DataBaseException("Cannot create order = " + entity);
        } finally {
            assert connection != null;
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            assert statement != null;
            try {
                statement.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
        return key;
    }


    @Override
    public Order read(int id) {
        Order order = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(READ_ORDER_BY_ORDER_ID)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                order = extractOrder(rs);
            }
        } catch (SQLException | NullPointerException e) {
            LOGGER.error(e.getMessage());
            throw new DataBaseException("Cannot read order, order_id = " + id);
        }
        return order;
    }

    private Order extractOrder(ResultSet rs) throws SQLException {
        return new Order(rs.getInt("id"),
                rs.getString("train_number"),
                CarriageType.valueOf(rs.getString("carriage_type")),
                rs.getDouble("price"),
                rs.getObject("arrival_date", LocalDateTime.class),
                rs.getObject("dispatch_date", LocalDateTime.class),
                extractUser(rs),
                rs.getObject("booking_date", LocalDateTime.class),
                OrderStatus.valueOf(rs.getString("status")),
                rs.getInt("seat_count"),
                rs.getString("arrival_station"),
                rs.getString("dispatch_station"),
                rs.getString("travel_time"),
                rs.getInt("route_id"),
                rs.getString("carriage_number"),
                rs.getString("seat_number"),
                rs.getString("seats_id"));
    }

    private User extractUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setUserId(rs.getInt("id"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));
        user.setFirstName(rs.getString("first_name"));
        user.setLastName((rs.getString("last_name")));
        user.setPhone(rs.getString("phone"));
        user.setBirthDate(rs.getObject("birth_date", LocalDate.class));
        user.setRole(Role.valueOf(rs.getString("role").toUpperCase()));
        user.setBlocked(rs.getBoolean("blocked"));
        return user;
    }

    @Override
    public boolean update(Order entity) {
        LOGGER.error("Unsupported operation exception");
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean delete(int id) {
        LOGGER.error("Unsupported operation exception");
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_ALL_ORDER)) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                orders.add(extractOrder(rs));
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DataBaseException("Can't get all order list.");
        }
        return orders;
    }

    @Override
    public boolean updateOrderStatus(int orderId, OrderStatus status) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_ORDER_STATUS)) {
            statement.setString(1, status.name());
            statement.setInt(2, orderId);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DataBaseException("Can`t update order. Order id = " + orderId + " status: " + status.name(), e);
        }
    }

    @Override
    public List<Order> getOrderByUserId(int userId) {
        List<Order> orders = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_ORDER_BY_USER_ID)) {
            statement.setInt(1, userId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                orders.add(extractOrder(rs));
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DataBaseException("Can't get order list by user ID. ID = " + userId, e);
        }
        return orders;
    }

    @Override
    public Double getPriceOfSuccessfulOrders(int userId) {
        double price = 0.0;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_THE_PRICE_OF_SUCCESSFUL_ORDERS)) {
            statement.setInt(1, userId);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                price = rs.getDouble("sum");
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DataBaseException("Can`t get price. user ID = " + userId, e);
        }
        return price;
    }


}
