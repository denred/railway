package com.epam.redkin.model.repository;


import com.epam.redkin.model.connectionpool.ConnectionPools;
import com.epam.redkin.model.entity.Station;
import com.epam.redkin.model.exception.DataBaseException;
import com.epam.redkin.model.repository.impl.StationRepositoryImpl;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class TestStationRepository implements TestConstants {
    private static Connection connection;
    private StationRepository stationRepository;

    @BeforeAll
    public static void setup() throws SQLException {
        connection = ConnectionPools.getTesting().getConnection();
    }

    @AfterAll
    static void close() throws SQLException {
        connection.close();
    }

    @BeforeEach
    public void init() throws SQLException {
        stationRepository = new StationRepositoryImpl(ConnectionPools.getTesting());
        connection.createStatement().executeUpdate(CREATE_STATION_TABLE);
        connection.createStatement().executeUpdate(INSERT_STATION1);
        connection.createStatement().executeUpdate(INSERT_STATION2);
        connection.createStatement().executeUpdate(INSERT_STATION3);
    }

    @AfterEach
    public void remove() throws SQLException {
        connection.createStatement().executeUpdate(REMOVE_STATION_TABLE);
    }

    @RepeatedTest(500)
    void testCreateAndReadStation() {
        String generatedString = RandomStringUtils.random(100, true, true);
        Station actual = new Station(generatedString);
        int id = stationRepository.create(actual);
        Station expected = stationRepository.read(id);

        assertEquals(expected, actual);
    }

    @Test
    void testCreateNullStation() {
        assertThrows(DataBaseException.class, () -> stationRepository.create(new Station(null)));
    }

    @Test
    void testCreateDuplicateStation() {
        assertThrows(DataBaseException.class, () -> stationRepository.create(stationRepository.read(1)));
    }

    @Test
    void testUpdateStation() {
        int id = 1;
        Station actual = stationRepository.read(id);
        actual.setStation("Tested");
        stationRepository.update(actual);
        assertEquals(stationRepository.read(id), actual);
    }

    @Test
    void testUpdateExistStation() {
        Station station = stationRepository.read(1);
        station.setId(3);
        assertThrows(DataBaseException.class, () -> stationRepository.update(station));
    }

    @Test
    void testDeleteStation() {
        Stream.of(1, 2, 3).forEach(i -> {
            assertTrue(stationRepository.delete(i));
            assertNull(stationRepository.read(i));
        });
    }

    @Test
    void testDeleteNonExistStation() {
        assertFalse(stationRepository.delete(108));
    }

    @Test
    void testGetListAllStations() {
        List<Station> actual = List.of(stationRepository.read(1), stationRepository.read(2), stationRepository.read(3));
        List<Station> expected = stationRepository.getAllStations();

        assertEquals(expected.size(), actual.size());
        assertArrayEquals(expected.stream()
                        .sorted(Comparator.comparingInt(Station::getId))
                        .toArray(),
                actual.stream()
                        .sorted(Comparator.comparingInt(Station::getId))
                        .toArray()
        );
    }

}
