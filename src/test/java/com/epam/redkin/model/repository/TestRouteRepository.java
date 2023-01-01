package com.epam.redkin.model.repository;

import com.epam.redkin.model.connectionpool.ConnectionPools;
import com.epam.redkin.model.dto.RouteInfoDTO;
import com.epam.redkin.model.dto.StationDTO;
import com.epam.redkin.model.entity.Route;
import com.epam.redkin.model.exception.DataBaseException;
import com.epam.redkin.model.repository.impl.RouteRepositoryImpl;
import com.epam.redkin.model.repository.impl.TrainRepositoryImpl;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class TestRouteRepository implements TestConstants {
    private static Connection connection;
    private RouteRepository routeRepository;

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
        routeRepository = new RouteRepositoryImpl(ConnectionPools.getTransactional());
        connection.createStatement().executeUpdate(CREATE_TRAIN_TABLE);
        connection.createStatement().executeUpdate(CREATE_ROUTE_TABLE);
        connection.createStatement().executeUpdate(INSERT_TRAIN1);
        connection.createStatement().executeUpdate(INSERT_TRAIN2);
        connection.createStatement().executeUpdate(INSERT_TRAIN3);
        connection.createStatement().executeUpdate(INSERT_ROUTE1);
        connection.createStatement().executeUpdate(INSERT_ROUTE2);
        connection.createStatement().executeUpdate(INSERT_ROUTE3);
    }

    @AfterEach
    public void remove() throws SQLException {
        connection.createStatement().executeUpdate(REMOVE_TRAIN_TABLE);
        connection.createStatement().executeUpdate(REMOVE_ROUTE_TABLE);
    }

    @RepeatedTest(100)
    void testCreateAndReadRoute() {
        Random random = new Random();
        int id = 1;
        int trainId = random.ints(1, 3)
                .findFirst()
                .getAsInt();
        int routeNumber = random.nextInt();
        String routeName = RandomStringUtils.random(64, true, true);

        Route actual = new Route(id, trainId, routeNumber, routeName);
        int expectedId = routeRepository.create(actual);
        assertEquals(routeRepository.read(expectedId), actual);
    }

    @Test
    void testCreateRouteWithNullParameter() {
        Route actual = new Route(1, 2, 3, null);
        assertThrows(DataBaseException.class, () -> routeRepository.create(actual));
    }

    @Test
    void testCreateExistUser() {
        Route exist = routeRepository.read(1);
        assertThrows(DataBaseException.class, () -> routeRepository.create(exist));
    }

    @Test
    void testReadNotExistRoute() {
        assertNull(routeRepository.read(Integer.MAX_VALUE));
    }

    @Test
    void testUpdateRoute() {
        Stream.of(1, 2, 3).forEach(i -> {
            Route actual = routeRepository.read(i);
            Random random = new Random();
            actual.setTrainId(1);
            actual.setRouteNumber(random.nextInt());
            actual.setRouteName("Test");
            routeRepository.update(actual);
            assertEquals(routeRepository.read(i), actual);
        });
    }

    @Test
    void testDeleteRoute() {
        Stream.of(1, 2, 3).forEach(i -> {
            assertTrue(routeRepository.delete(i));
            assertNull(routeRepository.read(i));
        });
    }

    @Test
    void testDeleteNotExistRoute() {
        assertFalse(routeRepository.delete(108));
    }

    @Test
    void testGetAllRouteInfoDTOList() {
        List<RouteInfoDTO> actual = new ArrayList<>();
        Stream.of(1, 2, 3).forEach(i ->
                actual.add(routeRepository.getRouteInfoDTOByRouteId(i))
        );
        List<RouteInfoDTO> expected = routeRepository.getAllRouteInfoDTOList();

        assertArrayEquals(expected.stream()
                        .sorted(Comparator.comparingInt(RouteInfoDTO::getRoutsId))
                        .toArray(),
                actual.stream()
                        .sorted(Comparator.comparingInt(RouteInfoDTO::getRoutsId))
                        .toArray());

    }


    @Test
    void testGetRouteInfoDTOByRouteId() {
        Stream.of(1, 2, 3).forEach(i -> {
            RouteInfoDTO actual = getRouteInfoDTO(i);
            RouteInfoDTO expected = routeRepository.getRouteInfoDTOByRouteId(i);
            assertEquals(expected, actual);
        });
    }


    private RouteInfoDTO getRouteInfoDTO(Integer i) {
        TrainRepository trainRepository = new TrainRepositoryImpl(ConnectionPools.getTransactional());
        return new RouteInfoDTO(i,
                routeRepository.read(i).getTrainId(),
                trainRepository.read(routeRepository.read(i).getTrainId()).getNumber(),
                routeRepository.read(i).getRouteName(),
                String.valueOf(routeRepository.read(i).getRouteNumber()));
    }

    @Test
    void testGetNotExistRouteInfoDTOByRouteId() {
        assertNull(routeRepository.getRouteInfoDTOByRouteId(1088));
    }

    /*
    * result.setStationId(resultSet.getInt("s.id"));
            result.setStation(resultSet.getString("station"));
            result.setOrder(resultSet.getInt("station_order"));
            result.setStationArrivalDate(resultSet.getObject("station_arrival", LocalDateTime.class));
            result.setStationDispatchData(resultSet.getObject("station_dispatch", LocalDateTime.class));
            result.setRoutName(resultSet.getString("r.name"));
            result.setRoutNumber(resultSet.getInt("r.number"));
            result.setRoutsId(resultSet.getInt("r.id"));
            result.setTrainId(resultSet.getInt("train_id"));
            result.setTrainNumber(resultSet.getString("t.number"));
            *
            *
            * StationDTO(int stationId,
            *           String station,
            *           int order,
            *           LocalDateTime stationArrivalDate,
            *           LocalDateTime stationDispatchData,
            *           String routName,
            *           int routNumber,
            *           int routsId,
            *           int trainId,
            *           String trainNumber)
            *
            *
            * String GET_ROUTE_LIST_WITH_PARAMETERS =
            * "SELECT r.name, r.number, r.id, station, " +
            "s.id, t.number, r.train_id, station_arrival, station_dispatch, pnr " +
            "FROM route as r " +
            "JOIN train as t on r.train_id = t.id " +
            "JOIN station_has_route as shr on shr.route_id = r.id " +
            "JOIN station as s on shr.station_id = s.id " +
            "WHERE station IN (?, ?) " +
            "ORDER BY station_dispatch, r.name , r.number";

            *
            *
    * */

    void testGetStationDTOListWithParameters() {
        //StationDTO stationDTO = routeRepository.getStationDTOListWithParameters();
    }
}
