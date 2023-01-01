package com.epam.redkin.model.repository;

import com.epam.redkin.model.connectionpool.ConnectionPools;
import com.epam.redkin.model.entity.CarriageType;
import com.epam.redkin.model.entity.OrderStatus;
import com.epam.redkin.model.entity.User;
import com.epam.redkin.model.entity.Order;
import com.epam.redkin.model.repository.impl.OrderRepositoryImpl;
import com.epam.redkin.model.repository.impl.UserRepositoryImpl;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

public class TestOrderRepository implements TestConstants {
    private static Connection connection;
    private OrderRepository orderRepository;

    @BeforeAll
    public static void setup() throws SQLException {
        connection = ConnectionPools.getTransactional().getConnection();
    }

    @AfterAll
    static void close() throws SQLException {
        connection.close();
    }

    @BeforeEach
    public void init() throws SQLException {
        orderRepository = new OrderRepositoryImpl(ConnectionPools.getTransactional());
        connection.createStatement().executeUpdate(CREATE_USER_TABLE);
        connection.createStatement().executeUpdate(CREATE_ORDER_TABLE);
        connection.createStatement().executeUpdate(INSERT_USER1);
        connection.createStatement().executeUpdate(INSERT_USER2);
        connection.createStatement().executeUpdate(INSERT_USER3);
    }

    @AfterEach
    public void remove() throws SQLException {
        connection.createStatement().executeUpdate(REMOVE_ORDER_TABLE);
        connection.createStatement().executeUpdate(REMOVE_USER_TABLE);
    }


    @RepeatedTest(10)
    void testCreateAndReadOrder() {
        Order actual = getOrder();
        int orderId = orderRepository.create(actual);
        actual.setId(orderId);
        Order expected = orderRepository.read(orderId);

        assertEquals(expected, actual);
    }

    private Order getOrder() {
        Random random = new Random();
        int id = random.nextInt(128) + 1;
        String trainNumber = RandomStringUtils.random(60, true, true);
        CarriageType carriageType = CarriageType.values()[random.nextInt(CarriageType.values().length)];
        double price = Math.ceil(random.nextDouble() * 100) / 100 + random.nextInt(100);
        LocalDateTime arrivalDate = LocalDateTime.of(2022, Month.APRIL, 1, 10, 45);
        LocalDateTime dispatchDate = LocalDateTime.of(2022, Month.APRIL, 12, 2, 45);
        User user = (new UserRepositoryImpl(ConnectionPools.getTransactional())).read(1);
        LocalDateTime orderDate = LocalDateTime.of(2022, Month.APRIL, 15, 2, 45);
        OrderStatus orderStatus = OrderStatus.values()[random.nextInt(OrderStatus.values().length)];
        int countOfSeats = random.nextInt(200) + 1;
        String arrivalStation = RandomStringUtils.random(64, true, true);
        String dispatchStation = RandomStringUtils.random(64, true, true);
        String travelTime = RandomStringUtils.random(128, true, true);
        int routeId = random.nextInt(128) + 1;
        String carriageNumber = RandomStringUtils.random(64, true, true);
        String seatNumber = RandomStringUtils.random(2000, true, true);
        String seatsId = RandomStringUtils.random(2000, true, true);

        return new Order(id, trainNumber, carriageType, price, arrivalDate, dispatchDate,
                user, orderDate, orderStatus, countOfSeats, arrivalStation, dispatchStation, travelTime,
                routeId, carriageNumber, seatNumber, seatsId);
    }


    @Test
    void testReadNotExistOrder() {
        assertNull(orderRepository.read(Integer.MAX_VALUE));
    }

    @Test
    void testGetAllOrders() {
        List<Order> actual = List.of(orderRepository.read(orderRepository.create(getOrder())),
                orderRepository.read(orderRepository.create(getOrder())),
                orderRepository.read(orderRepository.create(getOrder())));
        List<Order> expected = orderRepository.getAllOrders();
        assertEquals(expected.size(), actual.size());
        assertArrayEquals(expected.stream()
                        .sorted(Comparator.comparingInt(Order::getId))
                        .toArray(),
                actual.stream()
                        .sorted(Comparator.comparingInt(Order::getId))
                        .toArray());
    }

    @RepeatedTest(20)
    void testUpdateOrderStatus() {
        int id = orderRepository.create(getOrder());
        Order order = orderRepository.read(id);
        OrderStatus actual = order.getOrderStatus();
        OrderStatus orderStatus = Arrays.stream(OrderStatus.values())
                .filter(item -> !item.equals(actual))
                .findFirst()
                .orElse(OrderStatus.values()[0]);

        orderRepository.updateOrderStatus(id, orderStatus);

        OrderStatus expected = orderRepository.read(id).getOrderStatus();

        assertNotEquals(expected, actual);
        assertEquals(expected, orderStatus);
    }

    @Test
    void testGetOrderByUserId() {
        List<Order> actual = List.of(orderRepository.read(orderRepository.create(getOrder())),
                orderRepository.read(orderRepository.create(getOrder())),
                orderRepository.read(orderRepository.create(getOrder())));

        List<Order> expected = orderRepository.getOrderByUserId(1);
        assertEquals(expected.size(), actual.size());

        assertArrayEquals(expected.stream()
                        .sorted(Comparator.comparingInt(Order::getId))
                        .toArray(),
                actual.stream()
                        .sorted(Comparator.comparingInt(Order::getId))
                        .toArray());
    }

    @RepeatedTest(100)
    void testGetPriceOfSuccessfulOrders() {
        AtomicReference<Double> price = new AtomicReference<>(0.0);
        IntStream.range(1, 10).forEach(id -> {
            orderRepository.create(getOrder());
            orderRepository.updateOrderStatus(id, OrderStatus.CANCELED);
            if (id % 2 == 0) {
                orderRepository.updateOrderStatus(id, OrderStatus.ACCEPTED);
                price.updateAndGet(v -> v + orderRepository.read(id).getPrice());
            }
        });

        double expectedPrice = orderRepository.getPriceOfSuccessfulOrders(1);

        assertEquals(expectedPrice, price.get(), 0.01d);
    }

}
