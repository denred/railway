package com.epam.redkin.railway.model.repository.impl;

import com.epam.redkin.railway.model.builder.UserBuilder;
import com.epam.redkin.railway.model.entity.*;
import com.epam.redkin.railway.model.exception.DataBaseException;
import com.epam.redkin.railway.model.repository.OrderRepository;
import org.apache.commons.dbutils.DbUtils;
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
    private final DataSource dataSource;

    public OrderRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public int create(Order order) throws DataBaseException {
        LOGGER.info("Started the method create. Order: " + order);
        int key = -1;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);
            LOGGER.info("Transaction started");
            statement = connection.prepareStatement(ADD_ORDER, Statement.RETURN_GENERATED_KEYS);
            statement.setObject(1, order.getOrderDate());
            statement.setInt(2, order.getRouteId());
            statement.setString(3, order.getDispatchStation());
            statement.setObject(4, order.getDispatchDate());
            statement.setString(5, order.getArrivalStation());
            statement.setObject(6, order.getArrivalDate());
            statement.setString(7, order.getTravelTime());
            statement.setString(8, order.getTrainNumber());
            statement.setString(9, order.getCarriageNumber());
            statement.setString(10, order.getCarriageType().name());
            statement.setInt(11, order.getCountOfSeats());
            statement.setString(12, order.getSeatNumber());
            statement.setString(13, order.getSeatsId());
            statement.setInt(14, order.getUser().getUserId());
            statement.setDouble(15, order.getPrice());
            statement.setString(16, order.getOrderStatus().name());
            statement.executeUpdate();
            connection.commit();
            LOGGER.info("Transaction done");
            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                key = resultSet.getInt(1);
            }
            LOGGER.info("Generated id= " + key);
        } catch (SQLException | NullPointerException e) {
            try {
                connection.rollback();
                LOGGER.info("Transaction rollback");
            } catch (SQLException | NullPointerException ex) {
                LOGGER.error("Connection rollback error: " + ex);
                throw new DataBaseException("Connection rollback error: ", ex);
            }
            LOGGER.error("Cannot create order: " + e);
            throw new DataBaseException("Cannot create order = " + order, e);
        } finally {
            try {
                connection.setAutoCommit(true);
                DbUtils.close(resultSet);
                DbUtils.close(statement);
                DbUtils.close(connection);
                LOGGER.info("Connection closed");
            } catch (SQLException | NullPointerException e) {
                LOGGER.error("Connection closing error: " + e);
                throw new DataBaseException("Connection closing error: ", e);
            }
        }
        return key;
    }


    @Override
    public Order getById(int id) throws DataBaseException {
        LOGGER.info("Started the method getById with id= " + id);
        Order order = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(READ_ORDER_BY_ORDER_ID)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                order = extractOrder(rs);
            }
            LOGGER.info("The method getById done, order= " + order);
        } catch (SQLException | NullPointerException e) {
            LOGGER.error(e.getClass() + " in the method getById: " + e.getMessage());
            throw new DataBaseException("Cannot read order, order_id = " + id, e);
        }
        return order;
    }

    private Order extractOrder(ResultSet rs) throws SQLException {
        return Order.builder()
                .id(rs.getInt(ID))
                .trainNumber(rs.getString(TRAIN_NUMBER))
                .carriageType(CarriageType.valueOf(rs.getString(CARRIAGE_TYPE)))
                .price(rs.getDouble(PRICE))
                .arrivalDate(rs.getObject(ARRIVAL_DATE, LocalDateTime.class))
                .dispatchDate(rs.getObject(DISPATCH_DATE, LocalDateTime.class))
                .user(extractUser(rs))
                .orderDate(rs.getObject(BOOKING_DATE, LocalDateTime.class))
                .orderStatus(OrderStatus.valueOf(rs.getString(STATUS)))
                .countOfSeats(rs.getInt(SEATS_COUNT))
                .arrivalStation(rs.getString(ARRIVAL_STATION))
                .dispatchStation(rs.getString(DISPATCH_STATION))
                .travelTime(rs.getString(TRAVEL_TIME))
                .routeId(rs.getInt(ROUTE_ID))
                .carriageNumber(rs.getString(CARRIAGE_NUMBER))
                .seatNumber(rs.getString(SEAT_NUMBER))
                .seatsId(rs.getString(SEATS_ID))
                .build();
    }

    private User extractUser(ResultSet rs) throws SQLException {
        return new UserBuilder()
                .setUserId(rs.getInt(ID))
                .setEmail(rs.getString(EMAIL))
                .setPassword(rs.getString(PASSWORD))
                .setFirstName(rs.getString(FIRST_NAME))
                .setLastName(rs.getString(LAST_NAME))
                .setPhone(rs.getString(PHONE))
                .setBirthDate(rs.getObject(BIRTH_DATE, LocalDate.class))
                .setRole(Role.valueOf(rs.getString(ROLE).toUpperCase()))
                .setBlocked(rs.getBoolean(BLOCKED))
                .build();
    }

    @Override
    public boolean update(Order entity) {
        LOGGER.error("Unsupported operation exception");
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(int id) {
        LOGGER.error("Unsupported operation exception");
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Order> getAllOrders() throws DataBaseException {
        LOGGER.info("Started the method getAllOrders()");
        List<Order> orders = new ArrayList<>();
        ResultSet resultSet = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_ALL_ORDER)) {
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Order order = extractOrder(resultSet);
                orders.add(order);
            }
        } catch (SQLException | NullPointerException e) {
            LOGGER.error(e.getClass() + " in getAllOrders " + e);
            throw new DataBaseException("Can't get all order list.", e);
        } finally {
            try {
                DbUtils.close(resultSet);
            } catch (SQLException e) {
                LOGGER.error("Cannot close ResultSet " + e);
                throw new DataBaseException("Cannot close ResultSet.", e);
            }
        }
        LOGGER.info("\nExtracted orders: " + orders);
        return orders;
    }

    @Override
    public boolean updateOrderStatus(int orderId, OrderStatus status) throws DataBaseException {
        LOGGER.info("Started the method updateOrderStatus() with orderId= " + orderId
                + " and OrderStatus= " + status.name());
        Connection connection = null;
        PreparedStatement statement = null;
        boolean statusUpdate;
        try {
            LOGGER.info("Transaction started");
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(UPDATE_ORDER_STATUS);
            statement.setString(1, status.name());
            statement.setInt(2, orderId);
            statusUpdate = statement.executeUpdate() > 0;
            LOGGER.info("Transaction done with status: " + statusUpdate);
        } catch (SQLException e) {
            LOGGER.error(e.getClass() + " in updateOrderStatus(): " + e);
            throw new DataBaseException("Can`t update order. Order id = " + orderId + " status: " + status.name(), e);
        } finally {
            try {
                assert connection != null;
                connection.setAutoCommit(true);
                DbUtils.close(statement);
                DbUtils.close(connection);
                LOGGER.info("Connection closed");
            } catch (SQLException e) {
                LOGGER.error("Connection closing error: " + e);
                throw new DataBaseException("Connection closing error: ", e);
            }

        }
        return statusUpdate;
    }

    @Override
    public List<Order> getOrderByUserId(int userId) throws DataBaseException {
        LOGGER.info("Started the method getOrderByUserId() with userId= " + userId);
        List<Order> orders = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_ORDER_BY_USER_ID)) {
            statement.setInt(1, userId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                orders.add(extractOrder(rs));
            }
        } catch (SQLException e) {
            LOGGER.error(e.getClass() + " in getOrderByUserId(): " + e);
            throw new DataBaseException("Can't get order list by user ID. ID = " + userId, e);
        }
        LOGGER.info("The method getOrderByUserId() done, orders:\n" + orders);
        return orders;
    }

    @Override
    public Double getPriceOfSuccessfulOrders(int userId) throws DataBaseException {
        LOGGER.info("Started the method getPriceOfSuccessfulOrders() with userId= " + userId);
        double price = 0.0;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_THE_PRICE_OF_SUCCESSFUL_ORDERS)) {
            statement.setInt(1, userId);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                price = rs.getDouble("sum");
            }
        } catch (SQLException e) {
            LOGGER.error(e.getClass() + " in getPriceOfSuccessfulOrders(): " + e);
            throw new DataBaseException("Can`t get price. user ID = " + userId, e);
        }
        LOGGER.info("The method getPriceOfSuccessfulOrders() done, price: " + price);
        return price;
    }
}
