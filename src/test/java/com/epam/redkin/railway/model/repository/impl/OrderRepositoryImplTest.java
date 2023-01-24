package com.epam.redkin.railway.model.repository.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

import com.epam.redkin.railway.model.entity.*;
import com.epam.redkin.railway.model.exception.DataBaseException;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.sql.DataSource;

import com.epam.redkin.railway.model.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OrderRepositoryImplTest {
    @Mock
    private DataSource mockDataSource;
    @Mock
    private Connection mockConnection;
    @Mock
    private PreparedStatement mockStatement;
    @Mock
    private ResultSet mockResultSet;
    private OrderRepository orderRepository;

    @BeforeEach
    void setUp() throws SQLException {
        orderRepository = new OrderRepositoryImpl(mockDataSource);
        when(mockDataSource.getConnection()).thenReturn(mockConnection);
    }

    @Test
    void testCreatePositive() throws DataBaseException, SQLException {
        doNothing().when(mockConnection).setAutoCommit(false);
        doNothing().when(mockConnection).setAutoCommit(true);
        when(mockConnection.prepareStatement(anyString(), anyInt())).thenReturn(mockStatement);
        doNothing().when(mockStatement).setObject(anyInt(), any());
        doNothing().when(mockStatement).setString(anyInt(), anyString());
        doNothing().when(mockStatement).setInt(anyInt(), anyInt());
        doNothing().when(mockStatement).setDouble(anyInt(), anyDouble());
        when(mockStatement.executeUpdate()).thenReturn(1);
        doNothing().when(mockConnection).commit();
        when(mockStatement.getGeneratedKeys()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(Boolean.TRUE, Boolean.FALSE);
        when(mockResultSet.getInt(Statement.RETURN_GENERATED_KEYS)).thenReturn(1);
        doNothing().when(mockResultSet).close();
        doNothing().when(mockStatement).close();
        doNothing().when(mockConnection).close();

        assertEquals(1, orderRepository.create(
                Order.builder()
                        .orderDate(LocalDateTime.of(1, 1, 1, 1, 1))
                        .dispatchStation("StationA")
                        .arrivalStation("StationB")
                        .routeName("RouteName")
                        .dispatchDate(LocalDateTime.of(1, 1, 1, 1, 1))
                        .arrivalDate(LocalDateTime.of(1, 1, 1, 1, 1))
                        .travelTime(" ")
                        .trainNumber("111")
                        .carriageNumber("01")
                        .carriageType(CarriageType.FIRST_CLASS)
                        .seatsId("1 2 3")
                        .user(User.builder().build())
                        .orderStatus(OrderStatus.ACCEPTED)
                        .build()));
        verify(mockDataSource).getConnection();
        verify(mockConnection).setAutoCommit(false);
        verify(mockConnection).setAutoCommit(true);
        verify(mockConnection).commit();
        verify(mockConnection).close();
        verify(mockConnection).prepareStatement(anyString(), anyInt());

        verify(mockStatement, times(8)).setString(anyInt(), anyString());
        verify(mockStatement, times(3)).setInt(anyInt(), anyInt());
        verify(mockStatement, times(3)).setObject(anyInt(), any());
        verify(mockStatement).setDouble(anyInt(), anyDouble());
        verify(mockStatement).executeUpdate();
        verify(mockStatement).close();

        verify(mockResultSet).next();
        verify(mockResultSet).getInt(Statement.RETURN_GENERATED_KEYS);
        verify(mockResultSet).close();
    }

    @Test
    void testCreateNegative() throws DataBaseException, SQLException {
        doNothing().when(mockConnection).setAutoCommit(false);
        doNothing().when(mockConnection).setAutoCommit(true);
        when(mockConnection.prepareStatement(anyString(), anyInt())).thenReturn(mockStatement);
        doNothing().when(mockStatement).setObject(anyInt(), any());
        doNothing().when(mockStatement).setString(anyInt(), anyString());
        doNothing().when(mockStatement).setInt(anyInt(), anyInt());
        doNothing().when(mockStatement).setDouble(anyInt(), anyDouble());
        when(mockStatement.executeUpdate()).thenReturn(1);
        doNothing().when(mockConnection).commit();
        when(mockStatement.getGeneratedKeys()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(Boolean.FALSE);
        doNothing().when(mockStatement).close();
        doNothing().when(mockConnection).close();

        assertEquals(-1, orderRepository.create(
                Order.builder()
                        .orderDate(LocalDateTime.of(1, 1, 1, 1, 1))
                        .dispatchStation("StationA")
                        .arrivalStation("StationB")
                        .routeName("RouteName")
                        .dispatchDate(LocalDateTime.of(1, 1, 1, 1, 1))
                        .arrivalDate(LocalDateTime.of(1, 1, 1, 1, 1))
                        .travelTime(" ")
                        .trainNumber("111")
                        .carriageNumber("01")
                        .carriageType(CarriageType.FIRST_CLASS)
                        .seatsId("1 2 3")
                        .user(User.builder().build())
                        .orderStatus(OrderStatus.ACCEPTED)
                        .build()));
        verify(mockDataSource).getConnection();
        verify(mockConnection).setAutoCommit(false);
        verify(mockConnection).setAutoCommit(true);
        verify(mockConnection).commit();
        verify(mockConnection).close();
        verify(mockConnection).prepareStatement(anyString(), anyInt());

        verify(mockStatement, times(8)).setString(anyInt(), anyString());
        verify(mockStatement, times(3)).setInt(anyInt(), anyInt());
        verify(mockStatement, times(3)).setObject(anyInt(), any());
        verify(mockStatement).setDouble(anyInt(), anyDouble());
        verify(mockStatement).executeUpdate();
        verify(mockStatement).close();

        verify(mockResultSet).next();
    }

    @Test
    void testCreateThrowException() throws DataBaseException, SQLException {
        doNothing().when(mockConnection).setAutoCommit(false);
        doNothing().when(mockConnection).setAutoCommit(true);
        when(mockConnection.prepareStatement(anyString(), anyInt())).thenReturn(mockStatement);
        doNothing().when(mockStatement).setObject(anyInt(), any());
        doNothing().when(mockStatement).setString(anyInt(), anyString());
        doNothing().when(mockStatement).setInt(anyInt(), anyInt());
        doNothing().when(mockStatement).setDouble(anyInt(), anyDouble());
        when(mockStatement.executeUpdate()).thenThrow(SQLException.class);
        doNothing().when(mockStatement).close();
        doNothing().when(mockConnection).close();

        assertThrows(DataBaseException.class, () -> orderRepository.create(
                Order.builder()
                        .orderDate(LocalDateTime.of(1, 1, 1, 1, 1))
                        .dispatchStation("StationA")
                        .arrivalStation("StationB")
                        .routeName("RouteName")
                        .dispatchDate(LocalDateTime.of(1, 1, 1, 1, 1))
                        .arrivalDate(LocalDateTime.of(1, 1, 1, 1, 1))
                        .travelTime(" ")
                        .trainNumber("111")
                        .carriageNumber("01")
                        .carriageType(CarriageType.FIRST_CLASS)
                        .seatsId("1 2 3")
                        .user(User.builder().build())
                        .orderStatus(OrderStatus.ACCEPTED)
                        .build()));
        verify(mockDataSource).getConnection();
        verify(mockConnection).setAutoCommit(false);
        verify(mockConnection).setAutoCommit(true);
        verify(mockConnection).rollback();
        verify(mockConnection, times(0)).commit();
        verify(mockConnection).close();
        verify(mockConnection).prepareStatement(anyString(), anyInt());

        verify(mockStatement, times(8)).setString(anyInt(), anyString());
        verify(mockStatement, times(3)).setInt(anyInt(), anyInt());
        verify(mockStatement, times(3)).setObject(anyInt(), any());
        verify(mockStatement).setDouble(anyInt(), anyDouble());
        verify(mockStatement).executeUpdate();
        verify(mockStatement).close();

        verify(mockResultSet, times(0)).next();
        verify(mockResultSet, times(0)).getInt(Statement.RETURN_GENERATED_KEYS);
        verify(mockResultSet, times(0)).close();
    }

    @Test
    void testGetByIdPositive() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        doNothing().when(mockStatement).setInt(anyInt(), anyInt());
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(Boolean.TRUE, Boolean.FALSE);
        when(mockResultSet.getInt(anyString())).thenReturn(1, 1, 2, 3);
        when(mockResultSet.getString(anyString())).thenReturn("777", CarriageType.FIRST_CLASS.name(),
                "email@mail.com", "pass123", "John", "Smith", "+380671234567", "USER", "token",
                OrderStatus.ACCEPTED.name(), "StationA", "StationB", "travel", "01", "23", "1 2 3");
        when(mockResultSet.getObject(anyString(), eq(LocalDateTime.class))).thenReturn(LocalDateTime.of(1, 2, 3, 4, 5));
        when(mockResultSet.getObject(anyString(), eq(LocalDate.class))).thenReturn(LocalDate.of(1, 2, 3));
        when(mockResultSet.getDouble(anyString())).thenReturn(10.0);
        when(mockResultSet.getBoolean(anyString())).thenReturn(true);

        assertEquals(Order.builder()
                .id(1)
                .countOfSeats(2)
                .routeId(3)
                .trainNumber("777")
                .carriageType(CarriageType.FIRST_CLASS)
                .orderStatus(OrderStatus.ACCEPTED)
                .arrivalStation("StationA")
                .dispatchStation("StationB")
                .travelTime("travel")
                .carriageNumber("01")
                .seatNumber("23")
                .seatsId("1 2 3")
                .price(10.0)
                .dispatchDate(LocalDateTime.of(1, 2, 3, 4, 5))
                .arrivalDate(LocalDateTime.of(1, 2, 3, 4, 5))
                .orderDate(LocalDateTime.of(1, 2, 3, 4, 5))
                .user(User.builder()
                        .userId(1)
                        .email("email@mail.com")
                        .password("pass123")
                        .firstName("John")
                        .lastName("Smith")
                        .birthDate(LocalDate.of(1, 2, 3))
                        .phone("+380671234567")
                        .role(Role.USER)
                        .blocked(true)
                        .token("token")
                        .build())
                .build(), orderRepository.getById(1));
        verify(mockDataSource).getConnection();
        verify(mockConnection).prepareStatement(anyString());
        verify(mockStatement).setInt(anyInt(), anyInt());
        verify(mockStatement).executeQuery();
        verify(mockResultSet).next();
        verify(mockResultSet, times(4)).getInt(anyString());
        verify(mockResultSet, times(16)).getString(anyString());
        verify(mockResultSet, times(1)).getBoolean(anyString());
        verify(mockResultSet, times(1)).getDouble(anyString());
        verify(mockResultSet, times(3)).getObject(anyString(), eq(LocalDateTime.class));
        verify(mockResultSet, times(1)).getObject(anyString(), eq(LocalDate.class));
    }

    @Test
    void testGetByIdNegative() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        doNothing().when(mockStatement).setInt(anyInt(), anyInt());
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(Boolean.FALSE);

        assertNull(orderRepository.getById(1));
        verify(mockDataSource).getConnection();
        verify(mockConnection).prepareStatement(anyString());
        verify(mockStatement).setInt(anyInt(), anyInt());
        verify(mockStatement).executeQuery();
        verify(mockResultSet).next();
        verify(mockResultSet, times(0)).getInt(anyString());
        verify(mockResultSet, times(0)).getString(anyString());
        verify(mockResultSet, times(0)).getBoolean(anyString());
        verify(mockResultSet, times(0)).getDouble(anyString());
        verify(mockResultSet, times(0)).getObject(anyString(), eq(LocalDateTime.class));
        verify(mockResultSet, times(0)).getObject(anyString(), eq(LocalDate.class));
    }

    @Test
    void testGetByIdThrowException() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        doNothing().when(mockStatement).setInt(anyInt(), anyInt());
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenThrow(SQLException.class);

        assertThrows(DataBaseException.class, () -> orderRepository.getById(1));
        verify(mockDataSource).getConnection();
        verify(mockConnection).prepareStatement(anyString());
        verify(mockStatement).setInt(anyInt(), anyInt());
        verify(mockStatement).executeQuery();
        verify(mockResultSet).next();
        verify(mockResultSet, times(0)).getInt(anyString());
        verify(mockResultSet, times(0)).getString(anyString());
        verify(mockResultSet, times(0)).getBoolean(anyString());
        verify(mockResultSet, times(0)).getDouble(anyString());
        verify(mockResultSet, times(0)).getObject(anyString(), eq(LocalDateTime.class));
        verify(mockResultSet, times(0)).getObject(anyString(), eq(LocalDate.class));
    }

    @Test
    void testGetOrdersPositive() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(Boolean.TRUE, Boolean.FALSE);
        when(mockResultSet.getInt(anyString())).thenReturn(1, 1, 2, 3);
        when(mockResultSet.getString(anyString())).thenReturn("777", CarriageType.FIRST_CLASS.name(),
                "email@mail.com", "pass123", "John", "Smith", "+380671234567", "USER", "token",
                OrderStatus.ACCEPTED.name(), "StationA", "StationB", "travel", "01", "23", "1 2 3");
        when(mockResultSet.getObject(anyString(), eq(LocalDateTime.class))).thenReturn(LocalDateTime.of(1, 2, 3, 4, 5));
        when(mockResultSet.getObject(anyString(), eq(LocalDate.class))).thenReturn(LocalDate.of(1, 2, 3));
        when(mockResultSet.getDouble(anyString())).thenReturn(10.0);
        when(mockResultSet.getBoolean(anyString())).thenReturn(true);

        assertEquals(1, orderRepository.getOrders().size());
        verify(mockDataSource).getConnection();
        verify(mockConnection).prepareStatement(anyString());
        verify(mockStatement).executeQuery();
        verify(mockResultSet, times(2)).next();
        verify(mockResultSet).close();
        verify(mockResultSet, times(4)).getInt(anyString());
        verify(mockResultSet, times(16)).getString(anyString());
        verify(mockResultSet, times(1)).getBoolean(anyString());
        verify(mockResultSet, times(1)).getDouble(anyString());
        verify(mockResultSet, times(3)).getObject(anyString(), eq(LocalDateTime.class));
        verify(mockResultSet, times(1)).getObject(anyString(), eq(LocalDate.class));
    }

    @Test
    void testGetOrdersNegative() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(Boolean.FALSE);

        assertEquals(0, orderRepository.getOrders().size());
        verify(mockDataSource).getConnection();
        verify(mockConnection).prepareStatement(anyString());
        verify(mockStatement).executeQuery();
        verify(mockResultSet).next();
        verify(mockResultSet).close();
        verify(mockResultSet, times(0)).getInt(anyString());
        verify(mockResultSet, times(0)).getString(anyString());
        verify(mockResultSet, times(0)).getBoolean(anyString());
        verify(mockResultSet, times(0)).getDouble(anyString());
        verify(mockResultSet, times(0)).getObject(anyString(), eq(LocalDateTime.class));
        verify(mockResultSet, times(0)).getObject(anyString(), eq(LocalDate.class));
    }

    @Test
    void testGetOrdersThrowException() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenThrow(SQLException.class);

        assertThrows(DataBaseException.class, () -> orderRepository.getOrders());
        verify(mockDataSource).getConnection();
        verify(mockConnection).prepareStatement(anyString());
        verify(mockStatement).executeQuery();
        verify(mockResultSet).next();
        verify(mockResultSet).close();
        verify(mockResultSet, times(0)).getInt(anyString());
        verify(mockResultSet, times(0)).getString(anyString());
        verify(mockResultSet, times(0)).getBoolean(anyString());
        verify(mockResultSet, times(0)).getDouble(anyString());
        verify(mockResultSet, times(0)).getObject(anyString(), eq(LocalDateTime.class));
        verify(mockResultSet, times(0)).getObject(anyString(), eq(LocalDate.class));
    }

    @Test
    void testUpdateOrderStatusPositive() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        doNothing().when(mockConnection).setAutoCommit(false);
        doNothing().when(mockConnection).setAutoCommit(true);
        doNothing().when(mockStatement).setInt(anyInt(), anyInt());
        doNothing().when(mockStatement).setString(anyInt(), anyString());
        when(mockStatement.executeUpdate()).thenReturn(1);

        assertTrue(orderRepository.updateOrderStatus(1, OrderStatus.ACCEPTED));
        verify(mockDataSource).getConnection();
        verify(mockConnection).prepareStatement(anyString());
        verify(mockStatement).executeUpdate();
        verify(mockStatement).setInt(anyInt(), anyInt());
        verify(mockStatement).setString(anyInt(), anyString());
        verify(mockConnection).close();
        verify(mockConnection).commit();
        verify(mockConnection).setAutoCommit(false);
        verify(mockConnection).setAutoCommit(true);
        verify(mockStatement).close();
    }

    @Test
    void testUpdateOrderStatusThrowException() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        doNothing().when(mockConnection).setAutoCommit(false);
        doNothing().when(mockConnection).setAutoCommit(true);
        doNothing().when(mockStatement).setInt(anyInt(), anyInt());
        doNothing().when(mockStatement).setString(anyInt(), anyString());
        when(mockStatement.executeUpdate()).thenThrow(SQLException.class);

        assertThrows(DataBaseException.class, () -> orderRepository.updateOrderStatus(1, OrderStatus.ACCEPTED));
        verify(mockDataSource).getConnection();
        verify(mockConnection).prepareStatement(anyString());
        verify(mockConnection).setAutoCommit(false);
        verify(mockConnection).setAutoCommit(true);
        verify(mockConnection).rollback();
        verify(mockConnection).close();
        verify(mockStatement).executeUpdate();
        verify(mockStatement).setInt(anyInt(), anyInt());
        verify(mockStatement).setString(anyInt(), anyString());
        verify(mockStatement).close();
    }

    @Test
    void testGetOrderByUserIdPositive() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        doNothing().when(mockStatement).setInt(anyInt(), anyInt());
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(Boolean.TRUE, Boolean.FALSE);
        when(mockResultSet.getInt(anyString())).thenReturn(1, 1, 2, 3);
        when(mockResultSet.getString(anyString())).thenReturn("777", CarriageType.FIRST_CLASS.name(),
                "email@mail.com", "pass123", "John", "Smith", "+380671234567", "USER", "token",
                OrderStatus.ACCEPTED.name(), "StationA", "StationB", "travel", "01", "23", "1 2 3");
        when(mockResultSet.getObject(anyString(), eq(LocalDateTime.class))).thenReturn(LocalDateTime.of(1, 2, 3, 4, 5));
        when(mockResultSet.getObject(anyString(), eq(LocalDate.class))).thenReturn(LocalDate.of(1, 2, 3));
        when(mockResultSet.getDouble(anyString())).thenReturn(10.0);
        when(mockResultSet.getBoolean(anyString())).thenReturn(true);

        assertEquals(1, orderRepository.getOrderByUserId(1).size());
        verify(mockDataSource).getConnection();
        verify(mockConnection).prepareStatement(anyString());
        verify(mockStatement).setInt(anyInt(), anyInt());
        verify(mockStatement).executeQuery();
        verify(mockResultSet, times(2)).next();
        verify(mockResultSet, times(4)).getInt(anyString());
        verify(mockResultSet, times(16)).getString(anyString());
        verify(mockResultSet, times(1)).getBoolean(anyString());
        verify(mockResultSet, times(1)).getDouble(anyString());
        verify(mockResultSet, times(3)).getObject(anyString(), eq(LocalDateTime.class));
        verify(mockResultSet, times(1)).getObject(anyString(), eq(LocalDate.class));
    }

    @Test
    void testGetOrderByUserIdNegative() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        doNothing().when(mockStatement).setInt(anyInt(), anyInt());
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(Boolean.FALSE);

        assertEquals(0, orderRepository.getOrderByUserId(1).size());
        verify(mockDataSource).getConnection();
        verify(mockConnection).prepareStatement(anyString());
        verify(mockStatement).setInt(anyInt(), anyInt());
        verify(mockStatement).executeQuery();
        verify(mockResultSet, times(1)).next();
        verify(mockResultSet, times(0)).getInt(anyString());
        verify(mockResultSet, times(0)).getString(anyString());
        verify(mockResultSet, times(0)).getBoolean(anyString());
        verify(mockResultSet, times(0)).getDouble(anyString());
        verify(mockResultSet, times(0)).getObject(anyString(), eq(LocalDateTime.class));
        verify(mockResultSet, times(0)).getObject(anyString(), eq(LocalDate.class));
    }

    @Test
    void testGetOrderByUserIdThrowException() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        doNothing().when(mockStatement).setInt(anyInt(), anyInt());
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenThrow(SQLException.class);

        assertThrows(DataBaseException.class, () -> orderRepository.getOrderByUserId(1));
        verify(mockDataSource).getConnection();
        verify(mockConnection).prepareStatement(anyString());
        verify(mockStatement).setInt(anyInt(), anyInt());
        verify(mockStatement).executeQuery();
        verify(mockResultSet, times(1)).next();
        verify(mockResultSet, times(0)).getInt(anyString());
        verify(mockResultSet, times(0)).getString(anyString());
        verify(mockResultSet, times(0)).getBoolean(anyString());
        verify(mockResultSet, times(0)).getDouble(anyString());
        verify(mockResultSet, times(0)).getObject(anyString(), eq(LocalDateTime.class));
        verify(mockResultSet, times(0)).getObject(anyString(), eq(LocalDate.class));
    }

    @Test
    void testGetPriceOfSuccessfulOrdersPositive() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        doNothing().when(mockStatement).setInt(anyInt(), anyInt());
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(Boolean.TRUE, Boolean.FALSE);
        when(mockResultSet.getDouble(anyString())).thenReturn(10.0);

        assertEquals(10.0, orderRepository.getPriceOfSuccessfulOrders(1));
        verify(mockDataSource).getConnection();
        verify(mockConnection).prepareStatement(anyString());
        verify(mockStatement).setInt(anyInt(), anyInt());
        verify(mockStatement).executeQuery();
        verify(mockResultSet, times(1)).next();
        verify(mockResultSet, times(1)).getDouble(anyString());
    }

    @Test
    void testGetPriceOfSuccessfulOrdersNegative() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        doNothing().when(mockStatement).setInt(anyInt(), anyInt());
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(Boolean.FALSE);

        assertEquals(0.0, orderRepository.getPriceOfSuccessfulOrders(1));
        verify(mockDataSource).getConnection();
        verify(mockConnection).prepareStatement(anyString());
        verify(mockStatement).setInt(anyInt(), anyInt());
        verify(mockStatement).executeQuery();
        verify(mockResultSet, times(1)).next();
        verify(mockResultSet, times(0)).getDouble(anyString());
    }

    @Test
    void testGetPriceOfSuccessfulOrdersThrowException() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        doNothing().when(mockStatement).setInt(anyInt(), anyInt());
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenThrow(SQLException.class);

        assertThrows(DataBaseException.class, () -> orderRepository.getPriceOfSuccessfulOrders(1));
        verify(mockDataSource).getConnection();
        verify(mockConnection).prepareStatement(anyString());
        verify(mockStatement).setInt(anyInt(), anyInt());
        verify(mockStatement).executeQuery();
        verify(mockResultSet, times(1)).next();
        verify(mockResultSet, times(0)).getDouble(anyString());
    }
}

