package com.epam.redkin.model.repository;

import com.epam.redkin.model.connectionpool.ConnectionPools;
import com.epam.redkin.model.entity.RoutePoint;
import com.epam.redkin.model.repository.impl.RoutePointRepositoryImpl;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestRoutePointRepository implements TestConstants {
    private static Connection connection;
    private RoutePointRepository routePointRepository;

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
        routePointRepository = new RoutePointRepositoryImpl(ConnectionPools.getTransactional());
        connection.createStatement().executeUpdate(CREATE_STATION_TABLE);
        connection.createStatement().executeUpdate(CREATE_ROUTE_TABLE);
        connection.createStatement().executeUpdate(CREATE_ROUTE_POINT_TABLE);
        connection.createStatement().executeUpdate(INSERT_STATION1);
        connection.createStatement().executeUpdate(INSERT_STATION2);
        connection.createStatement().executeUpdate(INSERT_STATION3);
        connection.createStatement().executeUpdate(INSERT_ROUTE1);
        connection.createStatement().executeUpdate(INSERT_ROUTE2);
        connection.createStatement().executeUpdate(INSERT_ROUTE3);
        connection.createStatement().executeUpdate(INSERT_ROUTE_POINT1);
        connection.createStatement().executeUpdate(INSERT_ROUTE_POINT2);
        connection.createStatement().executeUpdate(INSERT_ROUTE_POINT3);
        connection.createStatement().executeUpdate(INSERT_ROUTE_POINT4);
        connection.createStatement().executeUpdate(INSERT_ROUTE_POINT5);
    }

    @AfterEach
    public void remove() throws SQLException {
        connection.createStatement().executeUpdate(REMOVE_ROUTE_POINT_TABLE);
        connection.createStatement().executeUpdate(REMOVE_STATION_TABLE);
        connection.createStatement().executeUpdate(REMOVE_ROUTE_TABLE);
    }

    @ParameterizedTest
    @CsvSource({"1,3", "2,2"})
    void testGetRoutePointListByRouteId(int expected, int actual) {
        assertEquals(routePointRepository.getRoutePointListByRouteId(expected).size(), actual);
    }

    @Test
    void testGetRoutePointListIfRoutePointNotExist() {
        assertEquals(routePointRepository.getRoutePointListByRouteId(Integer.MAX_VALUE).size(), 0);
    }

    @Test
    void testGetRoutePointList() {
        List<RoutePoint> actual = Stream.concat(routePointRepository.getRoutePointListByRouteId(1).stream(),
                        routePointRepository.getRoutePointListByRouteId(2).stream())
                .collect(Collectors.toList());
        List<RoutePoint> expected = routePointRepository.getRoutePointList();

        assertEquals(expected.size(), actual.size());
        assertArrayEquals(expected.stream()
                        .sorted(Comparator.comparingInt(RoutePoint::getRouteId))
                        .toArray(),
                actual.stream()
                        .sorted(Comparator.comparingInt(RoutePoint::getRouteId))
                        .toArray());
    }

    void testCreateRoutePoint() {
        Deque<RoutePoint> deque = new LinkedList<>();
        RoutePoint routePoint = getRoutePoint();
        deque.add(routePoint);


    }

    private RoutePoint getRoutePoint() {
        Random random = new Random();
        int stationId = random.nextInt(3);
        int routeId = random.nextInt(3);
        LocalDateTime arrival = LocalDateTime.of(2022, Month.APRIL, 15, 2, 45);
        LocalDateTime dispatch = LocalDateTime.of(2022, Month.APRIL, 15, 2, 45);
        int orderId = random.nextInt(10);

        return new RoutePoint(stationId, routeId, arrival, dispatch, orderId);
    }

    void testCreateExistRoutePoint() {
    }

    void testCreateRoutePointIfStationNotExist() {
    }

    @Test
    void testGetAllRouteToStationMappingList() {
    }

    void testUpdateRoutePointListByStationId() {
    }

    void testUpdateRoutePointListByStationIdIfStationNotExist() {
    }

    void testDeleteRoutePointByStationId() {
    }

    void testDeleteRoutePointByStationIdIfStationNotExist() {
    }

    void testGetMappingInfoByRouteIdAndStationId() {
    }

    void testGetMappingInfoByRouteIdAndStationIdIfStationAndRouteNotExist() {
    }

    void testGetMappingInfoListByRouteId() {
    }

}
