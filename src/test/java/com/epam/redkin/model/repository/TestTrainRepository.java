package com.epam.redkin.model.repository;

import com.epam.redkin.model.connectionpool.ConnectionPools;
import com.epam.redkin.model.entity.Train;
import com.epam.redkin.model.exception.DataBaseException;
import com.epam.redkin.model.repository.impl.TrainRepositoryImpl;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class TestTrainRepository implements TestConstants {
    private static Connection connection;
    private TrainRepository trainRepository;

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
        trainRepository = new TrainRepositoryImpl(ConnectionPools.getTransactional());
        connection.createStatement().executeUpdate(CREATE_TRAIN_TABLE);
        connection.createStatement().executeUpdate(INSERT_TRAIN1);
        connection.createStatement().executeUpdate(INSERT_TRAIN2);
        connection.createStatement().executeUpdate(INSERT_TRAIN3);
    }

    @AfterEach
    public void remove() throws SQLException {
        connection.createStatement().executeUpdate(REMOVE_TRAIN_TABLE);
    }

    static Stream<Arguments> arguments = Stream.of(
            Arguments.of(1, "01"),
            Arguments.of(1, "00"),
            Arguments.of(1, "s02"),
            Arguments.of(1, "0d3"),
            Arguments.of(1, "@04"),
            Arguments.of(1, "77west"),
            Arguments.of(1, "07"),
            Arguments.of(1, "0777"),
            Arguments.of(1, "107"),
            Arguments.of(1, "As1307")
    );

    @ParameterizedTest
    @VariableSource("arguments")
    void testCreateAndReadTrain(int id, String trainNumber) {
        Train actual = new Train(id, trainNumber);
        Train expected = trainRepository.read(trainRepository.create(actual));
        assertEquals(expected, actual);
    }


    @Test
    void testCreateTrainWithNullNumber() {
        assertThrows(DataBaseException.class, () -> trainRepository.create(new Train(null)));
    }

    @Test
    void testCreateTrainWithDuplicateNumber() {
        assertThrows(DataBaseException.class, () -> trainRepository.create(new Train("FAST")));
    }

    @Test
    void testUpdateTrain() {
        Train train = trainRepository.read(1);
        train.setNumber(train.getNumber() + 33);
        assertTrue(trainRepository.update(train));
        assertEquals(train, trainRepository.read(train.getId()));
    }

    @Test
    void testUpdateExistTrainNumber() {
        Train train = trainRepository.read(1);
        train.setId(3);
        assertThrows(DataBaseException.class, () -> trainRepository.update(train));
    }

    @Test
    void testDeleteTrain() {
        assertTrue(trainRepository.delete(1));
        assertNull(trainRepository.read(1));
    }

    @Test
    void testGetListOfTrains() {
        List<Train> trains = trainRepository.getAllTrains();
        trains.forEach(train ->
                assertEquals(train, trainRepository.read(train.getId())));
    }


}
