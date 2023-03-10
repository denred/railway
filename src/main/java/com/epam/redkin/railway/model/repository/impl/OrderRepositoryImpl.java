package com.epam.redkin.railway.model.repository.impl;

import com.epam.redkin.railway.model.dto.BookingDTO;
import com.epam.redkin.railway.model.dto.ReservationDTO;
import com.epam.redkin.railway.model.entity.*;
import com.epam.redkin.railway.model.exception.DataBaseException;
import com.epam.redkin.railway.model.repository.OrderRepository;
import com.epam.redkin.railway.util.constants.AppContextConstant;
import com.epam.redkin.railway.util.uuid.ReservationIDGenerator;
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
    public void create(Order order) throws DataBaseException {
        LOGGER.info("Started the method create. Order: " + order);
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);
            LOGGER.info("Transaction started");
            statement = connection.prepareStatement(ADD_ORDER);
            statement.setObject(1, ReservationIDGenerator.generateReservationID());
            statement.setObject(2, order.getOrderDate());
            statement.setInt(3, order.getRouteId());
            statement.setString(4, order.getDispatchStation());
            statement.setObject(5, order.getDispatchDate());
            statement.setString(6, order.getArrivalStation());
            statement.setObject(7, order.getArrivalDate());
            statement.setString(8, order.getTravelTime());
            statement.setString(9, order.getTrainNumber());
            statement.setString(10, order.getCarriageNumber());
            statement.setString(11, order.getCarriageType().name());
            statement.setInt(12, order.getCountOfSeats());
            statement.setString(13, order.getSeatNumber());
            statement.setString(14, order.getSeatsId());
            statement.setInt(15, order.getUser().getUserId());
            statement.setDouble(16, order.getPrice());
            statement.setString(17, order.getOrderStatus().name());
            statement.executeUpdate();
            connection.commit();
            LOGGER.info("Transaction done");
        } catch (SQLException | NullPointerException e) {
            try {
                assert connection != null;
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
                assert connection != null;
                connection.setAutoCommit(true);
                DbUtils.close(resultSet);
                DbUtils.close(statement);
                DbUtils.close(connection);
                LOGGER.info("Connection closed");
            } catch (SQLException | NullPointerException e) {
                LOGGER.error("Connection closing error: " + e);
            }
        }
    }


    @Override
    public Order getOrderByUUID(String uuid) throws DataBaseException {
        LOGGER.info("Started getByUUID(uuid={})", uuid);
        Order order = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_ORDER_BY_UUID)) {
            statement.setString(1, uuid);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                order = extractOrder(resultSet);
            }
            LOGGER.info("Extracted Order= " + order);
        } catch (SQLException | NullPointerException e) {
            LOGGER.error("Cannot extract order: " + e);
            throw new DataBaseException("Cannot extract order, order_id = " + uuid, e);
        }
        return order;
    }

    private Order extractOrder(ResultSet resultSet) throws SQLException {
        return Order.builder()
                .uuid(resultSet.getString(UUID))
                .trainNumber(resultSet.getString(TRAIN_NUMBER))
                .carriageType(CarriageType.valueOf(resultSet.getString(CARRIAGE_TYPE)))
                .price(resultSet.getDouble(PRICE))
                .arrivalDate(resultSet.getObject(ARRIVAL_DATE, LocalDateTime.class))
                .dispatchDate(resultSet.getObject(DISPATCH_DATE, LocalDateTime.class))
                .user(extractUser(resultSet))
                .orderDate(resultSet.getObject(BOOKING_DATE, LocalDateTime.class))
                .orderStatus(OrderStatus.valueOf(resultSet.getString(STATUS)))
                .countOfSeats(resultSet.getInt(SEATS_COUNT))
                .arrivalStation(resultSet.getString(ARRIVAL_STATION))
                .dispatchStation(resultSet.getString(DISPATCH_STATION))
                .travelTime(resultSet.getString(TRAVEL_TIME))
                .routeId(resultSet.getInt(ROUTE_ID))
                .carriageNumber(resultSet.getString(CARRIAGE_NUMBER))
                .seatNumber(resultSet.getString(SEAT_NUMBER))
                .seatsId(resultSet.getString(SEATS_ID))
                .routeName(resultSet.getString(ROUTE_NAME))
                .build();
    }

    private User extractUser(ResultSet resultSet) throws SQLException {
        return User.builder()
                .userId(resultSet.getInt("user.id"))
                .email(resultSet.getString(AppContextConstant.EMAIL))
                .password(resultSet.getString(AppContextConstant.PASSWORD))
                .firstName(resultSet.getString(AppContextConstant.FIRST_NAME))
                .lastName(resultSet.getString(AppContextConstant.LAST_NAME))
                .phone(resultSet.getString(AppContextConstant.PHONE_NUMBER))
                .birthDate(resultSet.getObject(AppContextConstant.BIRTH_DATE, LocalDate.class))
                .role(Role.valueOf(resultSet.getString(AppContextConstant.ROLE).toUpperCase()))
                .blocked(resultSet.getBoolean(AppContextConstant.BLOCKED))
                .token(resultSet.getString(AppContextConstant.LOGIN_TOKEN))
                .build();
    }


    @Override
    public List<Order> getOrders(int currentPage, int recordsPerPage) {
        LOGGER.info("Started get list of orders, currentPage={}, recordsPerPage={}", currentPage, recordsPerPage);
        List<Order> orders = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_ORDERS_PAGINATION)) {
            statement.setInt(1, currentPage);
            statement.setInt(2, recordsPerPage);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                orders.add(extractOrder(resultSet));
            }
            LOGGER.info("Extracted orders: " + orders);
            return orders;
        } catch (SQLException | NullPointerException e) {
            LOGGER.error("Cannot extract List<Order>: " + e);
            throw new DataBaseException("Cannot extract List<Order>", e);
        }
    }


    @Override
    public boolean updateOrderStatus(String orderUuid, OrderStatus status) throws DataBaseException {
        LOGGER.info("Started update order status, orderUuid={}, status={}", orderUuid, status.name());
        Connection connection = null;
        PreparedStatement statement = null;
        boolean statusUpdate;
        try {
            LOGGER.info("Transaction started");
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(UPDATE_ORDER_STATUS);
            statement.setString(1, status.name());
            statement.setString(2, orderUuid);
            statusUpdate = statement.executeUpdate() > 0;
            connection.commit();
            LOGGER.info("Transaction done with status: " + statusUpdate);
        } catch (SQLException | NullPointerException e) {
            try {
                assert connection != null;
                connection.rollback();
            } catch (SQLException | NullPointerException ex) {
                LOGGER.error("Connection rollback error:", e);
                throw new DataBaseException("Connection rollback error", ex);
            }
            LOGGER.error("Cannot update order status: " + e);
            throw new DataBaseException("Can`t update order status. Order id = " + orderUuid + " status: " + status.name(), e);
        } finally {
            try {
                assert connection != null;
                connection.setAutoCommit(true);
                DbUtils.close(statement);
                DbUtils.close(connection);
                LOGGER.info("Connection closed");
            } catch (SQLException | NullPointerException e) {
                LOGGER.error("Connection closing error: " + e);
            }
        }
        return statusUpdate;
    }

    @Override
    public List<Order> getOrderByUserId(int userId, int currentPage, int recordsPerPage) throws DataBaseException {
        LOGGER.info("Started method to get order by userId={}", userId);
        List<Order> orders = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_ORDER_BY_USER_ID_PAGINATION)) {
            statement.setInt(1, userId);
            statement.setInt(2, currentPage);
            statement.setInt(3, recordsPerPage);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                orders.add(extractOrder(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.error("Cannot get List<Order>: ", e);
            throw new DataBaseException("Can't get order list by user id | ID= " + userId, e);
        }
        LOGGER.info("\nThe method getOrderByUserId() done, orders: " + orders);
        return orders;
    }

    @Override
    public Double getSuccessfulOrdersPrice(int userId) throws DataBaseException {
        LOGGER.info("Started method get order price, userId={}", userId);
        double price = 0.0;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_THE_PRICE_OF_SUCCESSFUL_ORDERS)) {
            statement.setInt(1, userId);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                price = rs.getDouble(Constants.TOTAL);
            }
        } catch (SQLException e) {
            LOGGER.error("Cannot get price: " + e);
            throw new DataBaseException("Can`t get price. user id= " + userId, e);
        }
        LOGGER.info("Total order price: " + price);
        return price;
    }

    @Override
    public void addReservation(Connection connection, ReservationDTO reservationDTO) {
        try {
            PreparedStatement statement = connection.prepareStatement(INSERT_RESERVATION);
            statement.setString(1, reservationDTO.getUuid());
            statement.setString(2, reservationDTO.getStatus());
            statement.setInt(3, reservationDTO.getStationId());
            statement.setInt(4, reservationDTO.getSeatId());
            statement.setInt(5, reservationDTO.getTrainId());
            statement.setInt(6, reservationDTO.getRouteId());
            statement.setInt(7, reservationDTO.getSequenceNumber());
            statement.executeUpdate();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                LOGGER.error("Connection failed");
            }
            LOGGER.error("Failed to insert reservation: " + e.getMessage());
            throw new DataBaseException("Failed to insert reservation.", e);
        }
    }

    @Override
    public String saveBooking(Connection connection, BookingDTO bookingDTO) {
        LOGGER.info("Started saving booking");
        String uuid = ReservationIDGenerator.generateReservationID();
        try (PreparedStatement statement = connection.prepareStatement(SAVE_BOOKING)) {
            statement.setString(1, uuid);
            statement.setTimestamp(2, Timestamp.valueOf(bookingDTO.getBookingDate()));
            statement.setTimestamp(3, Timestamp.valueOf(bookingDTO.getDispatchDate()));
            statement.setTimestamp(4, Timestamp.valueOf(bookingDTO.getArrivalDate()));
            statement.setString(5, bookingDTO.getTravelTime());
            statement.setDouble(6, bookingDTO.getPrice());
            statement.setString(7, bookingDTO.getBookingStatus().toString());
            statement.setInt(8, bookingDTO.getUserId());
            statement.setInt(9, bookingDTO.getRouteId());
            statement.setInt(10, bookingDTO.getTrainId());
            statement.setInt(11, bookingDTO.getDispatchStationId());
            statement.setInt(12, bookingDTO.getArrivalStationId());
            statement.setInt(13, bookingDTO.getCarriageId());

            statement.executeUpdate();
            LOGGER.info("Booking saved successfully");
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                LOGGER.error("Connection failed");
            }
            LOGGER.error("Cannot save booking: " + e);
            throw new DataBaseException("Cannot save booking", e);
        }
        LOGGER.info("uuid={}", uuid);
        return uuid;
    }


    @Override
    public void saveBookingSeat(Connection connection, String bookingId, String seatId) {
        LOGGER.info("Started saving booked seat bookingId={}, seatId={}", bookingId, seatId);
        try (PreparedStatement statement = connection.prepareStatement(SAVE_BOOKED_SEAT)) {
            statement.setString(1, bookingId);
            statement.setString(2, seatId);
            int affectedRow = statement.executeUpdate();
            LOGGER.info("Number of rows affected {}", affectedRow);
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                LOGGER.error("Connection failed");
            }
            LOGGER.error("Saving booked seat error: " + e);
            throw new DataBaseException("Saving booked seat error", e);
        }
    }

    @Override
    public int getCountUserOrders(int userId) {
        LOGGER.info("Started get number of user orders userId={}", userId);
        int count = 0;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_COUNT_USER_ORDERS)) {
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                count = resultSet.getInt(COUNT);
            }
        } catch (SQLException e) {
            LOGGER.error("Cannot get number of user orders: ", e);
            throw new DataBaseException("Cannot get number of user orders with userId=" + userId, e);
        }
        LOGGER.info("Number of user orders: {}", count);
        return count;
    }

    @Override
    public int getCountOrders() {
        LOGGER.info("Started get number of user orders");
        int count = 0;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_COUNT_ORDERS)) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                count = resultSet.getInt(COUNT);
            }
        } catch (SQLException e) {
            LOGGER.error("Cannot get number of user orders: ", e);
            throw new DataBaseException("Cannot get number of user orders", e);
        }
        LOGGER.info("Number of user orders: {}", count);
        return count;
    }

    @Override
    public void changeUserBalance(Connection connection, int userId, double newBalance) {
        try (PreparedStatement statement = connection.prepareStatement(SET_NEW_BALANCE)) {
            statement.setDouble(1, newBalance);
            statement.setInt(2, userId);
            statement.executeUpdate();
            LOGGER.info("Balance successful change, new balance={}", newBalance);
        } catch (SQLException e) {

            try {
                connection.rollback();
            } catch (SQLException ex) {
                LOGGER.error("Connection failed");
            }
            LOGGER.error("Cannot change user balance");
            throw new DataBaseException("Cannot change user balance", e);
        }
    }

}
