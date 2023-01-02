package com.epam.redkin.model.repository;

import com.epam.redkin.model.connectionpool.ConnectionPools;
import com.epam.redkin.model.repository.impl.RoutePointRepositoryImpl;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.SQLException;

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


    void testGetRoutePointById(){}
    void testGetRoutePointIfNotExist(){}
    void testCreateRoutePoint(){}
    void testCreateExistRoutePoint(){}
    void testCreateRoutePointIfStationNotExist(){}

    @Test
    void testGetAllRouteToStationMappingList() {
    }

    void testUpdateRoutePointListByStationId(){}
    void testUpdateRoutePointListByStationIdIfStationNotExist(){}
    void testDeleteRoutePointByStationId(){}
    void testDeleteRoutePointByStationIdIfStationNotExist(){}
    void testGetMappingInfoByRouteIdAndStationId(){}
    void testGetMappingInfoByRouteIdAndStationIdIfStationAndRouteNotExist(){}
    void testGetMappingInfoListByRouteId(){}

}
