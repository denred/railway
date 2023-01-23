package com.epam.redkin.railway.model.repository.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

import com.epam.redkin.railway.model.entity.CarriageType;
import com.epam.redkin.railway.model.entity.Order;
import com.epam.redkin.railway.model.entity.OrderStatus;
import com.epam.redkin.railway.model.entity.User;
import com.epam.redkin.railway.model.exception.DataBaseException;

import java.sql.*;
import java.time.LocalDateTime;
import javax.sql.DataSource;

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;

class OrderRepositoryImplTest {

    @Test
    void testCreate() throws DataBaseException, SQLException {
        DataSource dataSource = mock(DataSource.class);
        when(dataSource.getConnection()).thenReturn(mock(Connection.class));
        Connection mockConnection = dataSource.getConnection();
       // when(mockConnection.prepareStatement(anyString(),anyInt())).thenReturn(mock(PreparedStatement.class));
        PreparedStatement mockStatement = mockConnection.prepareStatement(anyString(),anyInt());
        ResultSet mockResultSet = mock(ResultSet.class);
        OrderRepositoryImpl orderRepositoryImpl = new OrderRepositoryImpl(dataSource);
        assertThrows(DataBaseException.class, () -> orderRepositoryImpl.create(new Order()));
        //verify and assert
        verify(dataSource.getConnection(), times(2)).prepareStatement(anyString(), anyInt());
        verify(mockStatement, times(0)).setString(anyInt(), anyString());
        verify(mockStatement, times(0)).executeUpdate();
        verify(mockConnection, times(0)).commit();
        verify(mockResultSet, times(0)).next();
        verify(mockResultSet, times(0)).getInt(Statement.RETURN_GENERATED_KEYS);
    }

    /**
     * Method under test: {@link OrderRepositoryImpl#create(Order)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testCreate2() throws DataBaseException, SQLException {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.RuntimeException
        //       at org.apache.commons.dbutils.DbUtils.close(DbUtils.java:84)
        //       at com.epam.redkin.railway.model.repository.impl.OrderRepositoryImpl.create(OrderRepositoryImpl.java:79)
        //   See https://diff.blue/R013 to resolve this issue.

        new RuntimeException();
        new RuntimeException();
        new RuntimeException();
        new RuntimeException();
        DataSource dataSource = mock(DataSource.class);
        when(dataSource.getConnection()).thenReturn(mock(Connection.class));
        OrderRepositoryImpl orderRepositoryImpl = new OrderRepositoryImpl(dataSource);
        orderRepositoryImpl.create(new Order());
    }

    /**
     * Method under test: {@link OrderRepositoryImpl#create(Order)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testCreate3() throws DataBaseException, SQLException {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   com.epam.redkin.railway.model.exception.DAOException: cannot create order = Order{id=0, trainNumber='null', carrType=FIRST_CLASS, price=0.0, arrivalDate=null, dispatchDate=null, user=null, orderDate=null, orderStatus=null, countOfSeats=0, arrivalStation='null', dispatchStation='null', travelTime='null', routeId=0, carriageNumber='null', seatNumber='null', seatsId='null'}
        //       at com.epam.redkin.railway.model.repository.impl.OrderRepositoryImpl.create(OrderRepositoryImpl.java:73)
        //   java.lang.NullPointerException: Cannot invoke "com.epam.redkin.railway.model.entity.User.getUserId()" because the return value of "com.epam.redkin.railway.model.entity.Order.getUser()" is null
        //       at com.epam.redkin.railway.model.repository.impl.OrderRepositoryImpl.create(OrderRepositoryImpl.java:52)
        //   See https://diff.blue/R013 to resolve this issue.

        DataSource dataSource = mock(DataSource.class);
        when(dataSource.getConnection()).thenReturn(mock(Connection.class));
        OrderRepositoryImpl orderRepositoryImpl = new OrderRepositoryImpl(dataSource);

        Order order = new Order();
        order.setCarriageType(CarriageType.FIRST_CLASS);
        orderRepositoryImpl.create(order);
    }

    /**
     * Method under test: {@link OrderRepositoryImpl#create(Order)}
     */
    @Test
    void testCreate4() throws DataBaseException, SQLException {
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getInt(anyInt())).thenReturn(1);
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        doNothing().when(resultSet).close();
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(preparedStatement.executeUpdate()).thenReturn(1);
        when(preparedStatement.getGeneratedKeys()).thenReturn(resultSet);
        doNothing().when(preparedStatement).setDouble(anyInt(), anyDouble());
        doNothing().when(preparedStatement).setInt(anyInt(), anyInt());
        doNothing().when(preparedStatement).setObject(anyInt(), (Object) any());
        doNothing().when(preparedStatement).setString(anyInt(), (String) any());
        doNothing().when(preparedStatement).close();
        Connection connection = mock(Connection.class);
        doNothing().when(connection).commit();
        when(connection.prepareStatement((String) any(), anyInt())).thenReturn(preparedStatement);
        doNothing().when(connection).close();
        doNothing().when(connection).rollback();
        doNothing().when(connection).setAutoCommit(anyBoolean());
        DataSource dataSource = mock(DataSource.class);
        when(dataSource.getConnection()).thenReturn(connection);
        OrderRepositoryImpl orderRepositoryImpl = new OrderRepositoryImpl(dataSource);
        LocalDateTime arrivalDate = LocalDateTime.of(1, 1, 1, 1, 1);
        LocalDateTime dispatchDate = LocalDateTime.of(1, 1, 1, 1, 1);
        User user = new User();

        Order order = new Order(1, "42", CarriageType.FIRST_CLASS, 10.0d, arrivalDate, dispatchDate, user,
                LocalDateTime.of(1, 1, 1, 1, 1), OrderStatus.DECLINED, 3, "Arrival Station", "Dispatch Station",
                "Travel Time", 123, "42", "42", "42");
        order.setCarriageType(CarriageType.FIRST_CLASS);
        assertEquals(1, orderRepositoryImpl.create(order));
        verify(dataSource).getConnection();
        verify(connection).prepareStatement((String) any(), anyInt());
        verify(connection).close();
        verify(connection).commit();
        verify(connection, atLeast(1)).setAutoCommit(anyBoolean());
        verify(preparedStatement).executeUpdate();
        verify(preparedStatement).getGeneratedKeys();
        verify(preparedStatement).setDouble(anyInt(), anyDouble());
        verify(preparedStatement, atLeast(1)).setInt(anyInt(), anyInt());
        verify(preparedStatement, atLeast(1)).setObject(anyInt(), (Object) any());
        verify(preparedStatement, atLeast(1)).setString(anyInt(), (String) any());
        verify(preparedStatement).close();
        verify(resultSet).next();
        verify(resultSet).getInt(anyInt());
        verify(resultSet).close();
    }

    /**
     * Method under test: {@link OrderRepositoryImpl#create(Order)}
     */
    @Test
    void testCreate5() throws DataBaseException, SQLException {
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getInt(anyInt())).thenThrow(new SQLException());
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        doNothing().when(resultSet).close();
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(preparedStatement.executeUpdate()).thenReturn(1);
        when(preparedStatement.getGeneratedKeys()).thenReturn(resultSet);
        doNothing().when(preparedStatement).setDouble(anyInt(), anyDouble());
        doNothing().when(preparedStatement).setInt(anyInt(), anyInt());
        doNothing().when(preparedStatement).setObject(anyInt(), (Object) any());
        doNothing().when(preparedStatement).setString(anyInt(), (String) any());
        doNothing().when(preparedStatement).close();
        Connection connection = mock(Connection.class);
        doNothing().when(connection).commit();
        when(connection.prepareStatement((String) any(), anyInt())).thenReturn(preparedStatement);
        doNothing().when(connection).close();
        doNothing().when(connection).rollback();
        doNothing().when(connection).setAutoCommit(anyBoolean());
        DataSource dataSource = mock(DataSource.class);
        when(dataSource.getConnection()).thenReturn(connection);
        OrderRepositoryImpl orderRepositoryImpl = new OrderRepositoryImpl(dataSource);
        LocalDateTime arrivalDate = LocalDateTime.of(1, 1, 1, 1, 1);
        LocalDateTime dispatchDate = LocalDateTime.of(1, 1, 1, 1, 1);
        User user = new User();

        Order order = new Order(1, "42", CarriageType.FIRST_CLASS, 10.0d, arrivalDate, dispatchDate, user,
                LocalDateTime.of(1, 1, 1, 1, 1), OrderStatus.DECLINED, 3, "Arrival Station", "Dispatch Station",
                "Travel Time", 123, "42", "42", "42");
        order.setCarriageType(CarriageType.FIRST_CLASS);
        assertThrows(DataBaseException.class, () -> orderRepositoryImpl.create(order));
        verify(dataSource).getConnection();
        verify(connection).prepareStatement((String) any(), anyInt());
        verify(connection).close();
        verify(connection).commit();
        verify(connection).rollback();
        verify(connection, atLeast(1)).setAutoCommit(anyBoolean());
        verify(preparedStatement).executeUpdate();
        verify(preparedStatement).getGeneratedKeys();
        verify(preparedStatement).setDouble(anyInt(), anyDouble());
        verify(preparedStatement, atLeast(1)).setInt(anyInt(), anyInt());
        verify(preparedStatement, atLeast(1)).setObject(anyInt(), (Object) any());
        verify(preparedStatement, atLeast(1)).setString(anyInt(), (String) any());
        verify(preparedStatement).close();
        verify(resultSet).next();
        verify(resultSet).getInt(anyInt());
        verify(resultSet).close();
    }

    /**
     * Method under test: {@link OrderRepositoryImpl#create(Order)}
     */
    @Test
    void testCreate6() throws DataBaseException, SQLException {
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getInt(anyInt())).thenThrow(new DataBaseException("An error occurred"));
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        doNothing().when(resultSet).close();
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(preparedStatement.executeUpdate()).thenReturn(1);
        when(preparedStatement.getGeneratedKeys()).thenReturn(resultSet);
        doNothing().when(preparedStatement).setDouble(anyInt(), anyDouble());
        doNothing().when(preparedStatement).setInt(anyInt(), anyInt());
        doNothing().when(preparedStatement).setObject(anyInt(), (Object) any());
        doNothing().when(preparedStatement).setString(anyInt(), (String) any());
        doNothing().when(preparedStatement).close();
        Connection connection = mock(Connection.class);
        doNothing().when(connection).commit();
        when(connection.prepareStatement((String) any(), anyInt())).thenReturn(preparedStatement);
        doNothing().when(connection).close();
        doNothing().when(connection).rollback();
        doNothing().when(connection).setAutoCommit(anyBoolean());
        DataSource dataSource = mock(DataSource.class);
        when(dataSource.getConnection()).thenReturn(connection);
        OrderRepositoryImpl orderRepositoryImpl = new OrderRepositoryImpl(dataSource);
        LocalDateTime arrivalDate = LocalDateTime.of(1, 1, 1, 1, 1);
        LocalDateTime dispatchDate = LocalDateTime.of(1, 1, 1, 1, 1);
        User user = new User();

        Order order = new Order(1, "42", CarriageType.FIRST_CLASS, 10.0d, arrivalDate, dispatchDate, user,
                LocalDateTime.of(1, 1, 1, 1, 1), OrderStatus.DECLINED, 3, "Arrival Station", "Dispatch Station",
                "Travel Time", 123, "42", "42", "42");
        order.setCarriageType(CarriageType.FIRST_CLASS);
        assertThrows(DataBaseException.class, () -> orderRepositoryImpl.create(order));
        verify(dataSource).getConnection();
        verify(connection).prepareStatement((String) any(), anyInt());
        verify(connection).close();
        verify(connection).commit();
        verify(connection, atLeast(1)).setAutoCommit(anyBoolean());
        verify(preparedStatement).executeUpdate();
        verify(preparedStatement).getGeneratedKeys();
        verify(preparedStatement).setDouble(anyInt(), anyDouble());
        verify(preparedStatement, atLeast(1)).setInt(anyInt(), anyInt());
        verify(preparedStatement, atLeast(1)).setObject(anyInt(), (Object) any());
        verify(preparedStatement, atLeast(1)).setString(anyInt(), (String) any());
        verify(preparedStatement).close();
        verify(resultSet).next();
        verify(resultSet).getInt(anyInt());
        verify(resultSet).close();
    }

    /**
     * Method under test: {@link OrderRepositoryImpl#create(Order)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testCreate7() throws DataBaseException, SQLException {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.RuntimeException
        //       at org.apache.commons.dbutils.DbUtils.close(DbUtils.java:72)
        //       at com.epam.redkin.railway.model.repository.impl.OrderRepositoryImpl.create(OrderRepositoryImpl.java:78)
        //   See https://diff.blue/R013 to resolve this issue.

        new RuntimeException();
        new RuntimeException();
        DataSource dataSource = mock(DataSource.class);
        when(dataSource.getConnection()).thenReturn(mock(Connection.class));
        OrderRepositoryImpl orderRepositoryImpl = new OrderRepositoryImpl(dataSource);
        LocalDateTime arrivalDate = LocalDateTime.of(1, 1, 1, 1, 1);
        LocalDateTime dispatchDate = LocalDateTime.of(1, 1, 1, 1, 1);
        User user = new User();

        Order order = new Order(123, "42", CarriageType.FIRST_CLASS, 10.0d, arrivalDate, dispatchDate, user,
                LocalDateTime.of(1, 1, 1, 1, 1), OrderStatus.DECLINED, 3, "Arrival Station", "Dispatch Station",
                "Travel Time", 123, "42", "42", "42");
        order.setCarriageType(CarriageType.FIRST_CLASS);
        orderRepositoryImpl.create(order);
    }

    @Test
    void testGetById() throws DataBaseException, SQLException {
        DataSource dataSource = mock(DataSource.class);
        PreparedStatement mockStatement = mock(PreparedStatement.class);
        when(dataSource.getConnection()).thenReturn(mock(Connection.class));
    }

    /**
     * Method under test: {@link OrderRepositoryImpl#getById(int)}
     */
    @Test
    void testGetById2() throws DataBaseException, SQLException {
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getInt((String) any())).thenThrow(new RuntimeException());
        when(resultSet.getString((String) any())).thenThrow(new RuntimeException());
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        doNothing().when(preparedStatement).setInt(anyInt(), anyInt());
        doNothing().when(preparedStatement).close();
        Connection connection = mock(Connection.class);
        when(connection.prepareStatement((String) any())).thenReturn(preparedStatement);
        doNothing().when(connection).close();
        DataSource dataSource = mock(DataSource.class);
        when(dataSource.getConnection()).thenReturn(connection);
        assertThrows(RuntimeException.class, () -> (new OrderRepositoryImpl(dataSource)).getById(1));
        verify(dataSource).getConnection();
        verify(connection).prepareStatement((String) any());
        verify(connection).close();
        verify(preparedStatement).executeQuery();
        verify(preparedStatement).setInt(anyInt(), anyInt());
        verify(preparedStatement).close();
        verify(resultSet).next();
        verify(resultSet).getInt((String) any());
    }

    /**
     * Method under test: {@link OrderRepositoryImpl#update(Order)}
     */
    @Test
    void testUpdate() {
        OrderRepositoryImpl orderRepositoryImpl = new OrderRepositoryImpl(mock(DataSource.class));
        assertThrows(UnsupportedOperationException.class, () -> orderRepositoryImpl.update(new Order()));
    }

    /**
     * Method under test: {@link OrderRepositoryImpl#delete(int)}
     */
    @Test
    void testDelete() {
        assertThrows(UnsupportedOperationException.class,
                () -> (new OrderRepositoryImpl(mock(DataSource.class))).delete(1));
    }

    /**
     * Method under test: {@link OrderRepositoryImpl#getAllOrders()}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetAllOrders() throws DataBaseException, SQLException {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.IllegalArgumentException: No enum constant com.epam.redkin.railway.model.entity.CarriageType.String
        //       at java.lang.Enum.valueOf(Enum.java:273)
        //       at com.epam.redkin.railway.model.entity.CarriageType.valueOf(CarriageType.java:3)
        //       at com.epam.redkin.railway.model.builder.OrderBuilder.setCarriageType(OrderBuilder.java:134)
        //       at com.epam.redkin.railway.model.repository.impl.OrderRepositoryImpl.extractOrder(OrderRepositoryImpl.java:114)
        //       at com.epam.redkin.railway.model.repository.impl.OrderRepositoryImpl.getAllOrders(OrderRepositoryImpl.java:168)
        //   See https://diff.blue/R013 to resolve this issue.

        DataSource dataSource = mock(DataSource.class);
        when(dataSource.getConnection()).thenReturn(mock(Connection.class));
        (new OrderRepositoryImpl(dataSource)).getAllOrders();
    }

    /**
     * Method under test: {@link OrderRepositoryImpl#getAllOrders()}
     */
    @Test
    void testGetAllOrders2() throws DataBaseException, SQLException {
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getInt((String) any())).thenThrow(new RuntimeException());
        when(resultSet.getString((String) any())).thenThrow(new RuntimeException());
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        doNothing().when(resultSet).close();
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        doNothing().when(preparedStatement).close();
        Connection connection = mock(Connection.class);
        when(connection.prepareStatement((String) any())).thenReturn(preparedStatement);
        doNothing().when(connection).close();
        DataSource dataSource = mock(DataSource.class);
        when(dataSource.getConnection()).thenReturn(connection);
        assertThrows(RuntimeException.class, () -> (new OrderRepositoryImpl(dataSource)).getAllOrders());
        verify(dataSource).getConnection();
        verify(connection).prepareStatement((String) any());
        verify(connection).close();
        verify(preparedStatement).executeQuery();
        verify(preparedStatement).close();
        verify(resultSet).next();
        verify(resultSet).getInt((String) any());
        verify(resultSet).close();
    }

    /**
     * Method under test: {@link OrderRepositoryImpl#updateOrderStatus(int, OrderStatus)}
     */
    @Test
    void testUpdateOrderStatus() throws DataBaseException, SQLException {
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(preparedStatement.executeUpdate()).thenReturn(1);
        doNothing().when(preparedStatement).setInt(anyInt(), anyInt());
        doNothing().when(preparedStatement).setString(anyInt(), (String) any());
        doNothing().when(preparedStatement).close();
        Connection connection = mock(Connection.class);
        when(connection.prepareStatement((String) any())).thenReturn(preparedStatement);
        doNothing().when(connection).close();
        doNothing().when(connection).setAutoCommit(anyBoolean());
        DataSource dataSource = mock(DataSource.class);
        when(dataSource.getConnection()).thenReturn(connection);
        assertTrue((new OrderRepositoryImpl(dataSource)).updateOrderStatus(123, OrderStatus.DECLINED));
        verify(dataSource).getConnection();
        verify(connection).prepareStatement((String) any());
        verify(connection).close();
        verify(connection, atLeast(1)).setAutoCommit(anyBoolean());
        verify(preparedStatement).executeUpdate();
        verify(preparedStatement).setInt(anyInt(), anyInt());
        verify(preparedStatement).setString(anyInt(), (String) any());
        verify(preparedStatement).close();
    }

    /**
     * Method under test: {@link OrderRepositoryImpl#updateOrderStatus(int, OrderStatus)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testUpdateOrderStatus2() throws DataBaseException, SQLException {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.RuntimeException
        //       at org.apache.commons.dbutils.DbUtils.close(DbUtils.java:84)
        //       at com.epam.redkin.railway.model.repository.impl.OrderRepositoryImpl.updateOrderStatus(OrderRepositoryImpl.java:210)
        //   See https://diff.blue/R013 to resolve this issue.

        new RuntimeException();
        new RuntimeException();
        new RuntimeException();
        new RuntimeException();
        DataSource dataSource = mock(DataSource.class);
        when(dataSource.getConnection()).thenReturn(mock(Connection.class));
        (new OrderRepositoryImpl(dataSource)).updateOrderStatus(123, OrderStatus.DECLINED);
    }

    /**
     * Method under test: {@link OrderRepositoryImpl#getOrderByUserId(int)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetOrderByUserId() throws DataBaseException, SQLException {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.IllegalArgumentException: No enum constant com.epam.redkin.railway.model.entity.CarriageType.String
        //       at java.lang.Enum.valueOf(Enum.java:273)
        //       at com.epam.redkin.railway.model.entity.CarriageType.valueOf(CarriageType.java:3)
        //       at com.epam.redkin.railway.model.builder.OrderBuilder.setCarriageType(OrderBuilder.java:134)
        //       at com.epam.redkin.railway.model.repository.impl.OrderRepositoryImpl.extractOrder(OrderRepositoryImpl.java:114)
        //       at com.epam.redkin.railway.model.repository.impl.OrderRepositoryImpl.getOrderByUserId(OrderRepositoryImpl.java:231)
        //   See https://diff.blue/R013 to resolve this issue.

        DataSource dataSource = mock(DataSource.class);
        when(dataSource.getConnection()).thenReturn(mock(Connection.class));
        (new OrderRepositoryImpl(dataSource)).getOrderByUserId(123);
    }

    /**
     * Method under test: {@link OrderRepositoryImpl#getOrderByUserId(int)}
     */
    @Test
    void testGetOrderByUserId2() throws DataBaseException, SQLException {
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getInt((String) any())).thenThrow(new RuntimeException());
        when(resultSet.getString((String) any())).thenThrow(new RuntimeException());
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        doNothing().when(preparedStatement).setInt(anyInt(), anyInt());
        doNothing().when(preparedStatement).close();
        Connection connection = mock(Connection.class);
        when(connection.prepareStatement((String) any())).thenReturn(preparedStatement);
        doNothing().when(connection).close();
        DataSource dataSource = mock(DataSource.class);
        when(dataSource.getConnection()).thenReturn(connection);
        assertThrows(RuntimeException.class, () -> (new OrderRepositoryImpl(dataSource)).getOrderByUserId(123));
        verify(dataSource).getConnection();
        verify(connection).prepareStatement((String) any());
        verify(connection).close();
        verify(preparedStatement).executeQuery();
        verify(preparedStatement).setInt(anyInt(), anyInt());
        verify(preparedStatement).close();
        verify(resultSet).next();
        verify(resultSet).getInt((String) any());
    }

    /**
     * Method under test: {@link OrderRepositoryImpl#getOrderByUserId(int)}
     */
    @Test
    void testGetOrderByUserId3() throws DataBaseException, SQLException {
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.next()).thenThrow(new SQLException());
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        doNothing().when(preparedStatement).setInt(anyInt(), anyInt());
        doNothing().when(preparedStatement).close();
        Connection connection = mock(Connection.class);
        when(connection.prepareStatement((String) any())).thenReturn(preparedStatement);
        doNothing().when(connection).close();
        DataSource dataSource = mock(DataSource.class);
        when(dataSource.getConnection()).thenReturn(connection);
        assertThrows(DataBaseException.class, () -> (new OrderRepositoryImpl(dataSource)).getOrderByUserId(1));
        verify(dataSource).getConnection();
        verify(connection).prepareStatement((String) any());
        verify(connection).close();
        verify(preparedStatement).executeQuery();
        verify(preparedStatement).setInt(anyInt(), anyInt());
        verify(preparedStatement).close();
        verify(resultSet).next();
    }

    /**
     * Method under test: {@link OrderRepositoryImpl#getPriceOfSuccessfulOrders(int)}
     */
    @Test
    void testGetPriceOfSuccessfulOrders() throws DataBaseException, SQLException {
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getDouble((String) any())).thenReturn(10.0d);
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        doNothing().when(preparedStatement).setInt(anyInt(), anyInt());
        doNothing().when(preparedStatement).close();
        Connection connection = mock(Connection.class);
        when(connection.prepareStatement((String) any())).thenReturn(preparedStatement);
        doNothing().when(connection).close();
        DataSource dataSource = mock(DataSource.class);
        when(dataSource.getConnection()).thenReturn(connection);
        assertEquals(10.0d, (new OrderRepositoryImpl(dataSource)).getPriceOfSuccessfulOrders(123).doubleValue());
        verify(dataSource).getConnection();
        verify(connection).prepareStatement((String) any());
        verify(connection).close();
        verify(preparedStatement).executeQuery();
        verify(preparedStatement).setInt(anyInt(), anyInt());
        verify(preparedStatement).close();
        verify(resultSet).next();
        verify(resultSet).getDouble((String) any());
    }

    /**
     * Method under test: {@link OrderRepositoryImpl#getPriceOfSuccessfulOrders(int)}
     */
    @Test
    void testGetPriceOfSuccessfulOrders2() throws DataBaseException, SQLException {
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getDouble((String) any())).thenThrow(new SQLException());
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        doNothing().when(preparedStatement).setInt(anyInt(), anyInt());
        doNothing().when(preparedStatement).close();
        Connection connection = mock(Connection.class);
        when(connection.prepareStatement((String) any())).thenReturn(preparedStatement);
        doNothing().when(connection).close();
        DataSource dataSource = mock(DataSource.class);
        when(dataSource.getConnection()).thenReturn(connection);
        assertThrows(DataBaseException.class, () -> (new OrderRepositoryImpl(dataSource)).getPriceOfSuccessfulOrders(123));
        verify(dataSource).getConnection();
        verify(connection).prepareStatement((String) any());
        verify(connection).close();
        verify(preparedStatement).executeQuery();
        verify(preparedStatement).setInt(anyInt(), anyInt());
        verify(preparedStatement).close();
        verify(resultSet).next();
        verify(resultSet).getDouble((String) any());
    }

    /**
     * Method under test: {@link OrderRepositoryImpl#getPriceOfSuccessfulOrders(int)}
     */
    @Test
    void testGetPriceOfSuccessfulOrders3() throws DataBaseException, SQLException {
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getDouble((String) any())).thenThrow(new DataBaseException("An error occurred"));
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        doNothing().when(preparedStatement).setInt(anyInt(), anyInt());
        doNothing().when(preparedStatement).close();
        Connection connection = mock(Connection.class);
        when(connection.prepareStatement((String) any())).thenReturn(preparedStatement);
        doNothing().when(connection).close();
        DataSource dataSource = mock(DataSource.class);
        when(dataSource.getConnection()).thenReturn(connection);
        assertThrows(DataBaseException.class, () -> (new OrderRepositoryImpl(dataSource)).getPriceOfSuccessfulOrders(123));
        verify(dataSource).getConnection();
        verify(connection).prepareStatement((String) any());
        verify(connection).close();
        verify(preparedStatement).executeQuery();
        verify(preparedStatement).setInt(anyInt(), anyInt());
        verify(preparedStatement).close();
        verify(resultSet).next();
        verify(resultSet).getDouble((String) any());
    }

    /**
     * Method under test: {@link OrderRepositoryImpl#getPriceOfSuccessfulOrders(int)}
     */
    @Test
    void testGetPriceOfSuccessfulOrders4() throws DataBaseException, SQLException {
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.next()).thenThrow(new SQLException());
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        doNothing().when(preparedStatement).setInt(anyInt(), anyInt());
        doNothing().when(preparedStatement).close();
        Connection connection = mock(Connection.class);
        when(connection.prepareStatement((String) any())).thenReturn(preparedStatement);
        doNothing().when(connection).close();
        DataSource dataSource = mock(DataSource.class);
        when(dataSource.getConnection()).thenReturn(connection);
        assertThrows(DataBaseException.class, () -> (new OrderRepositoryImpl(dataSource)).getPriceOfSuccessfulOrders(1));
        verify(dataSource).getConnection();
        verify(connection).prepareStatement((String) any());
        verify(connection).close();
        verify(preparedStatement).executeQuery();
        verify(preparedStatement).setInt(anyInt(), anyInt());
        verify(preparedStatement).close();
        verify(resultSet).next();
    }
}

