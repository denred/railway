package com.epam.redkin.model.repository;

import com.epam.redkin.model.connectionpool.ConnectionPools;
import com.epam.redkin.model.entity.Carriage;
import com.epam.redkin.model.entity.CarriageType;

import com.epam.redkin.model.exception.DataBaseException;
import com.epam.redkin.model.repository.impl.CarriageRepoImpl;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;


import java.sql.Connection;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class TestCarriageRepository implements TestConstants {
    private static Connection connection;
    private CarriageRepo carriageRepo;
    private static List<Carriage> carriages;

    @BeforeEach
    public void init() throws SQLException {
        carriageRepo = new CarriageRepoImpl(ConnectionPools.getTransactional());
        connection.createStatement().executeUpdate(CREATE_TRAIN_TABLE);
        connection.createStatement().executeUpdate(CREATE_CARRIAGE_TABLE);
        connection.createStatement().executeUpdate(INSERT_TRAIN1);
        connection.createStatement().executeUpdate(INSERT_TRAIN2);
        connection.createStatement().executeUpdate(INSERT_TRAIN3);
    }

    @AfterEach
    public void remove() throws SQLException {
        connection.createStatement().executeUpdate(REMOVE_CARRIAGE_TABLE);
        connection.createStatement().executeUpdate(REMOVE_TRAIN_TABLE);
    }

    @AfterAll
    static void close() throws SQLException {
        connection.close();
    }

    @BeforeAll
    public static void setup() throws SQLException {
        connection = ConnectionPools.getTransactional().getConnection();
        carriages = new ArrayList<>();
    }

    static Stream<Arguments> arguments = Stream.of(
            Arguments.of(1, CarriageType.FIRST_CLASS, "07", 1),
            Arguments.of(1, CarriageType.FIRST_CLASS, "08", 1),
            Arguments.of(1, CarriageType.SECOND_CLASS, "09", 1),
            Arguments.of(1, CarriageType.SECOND_CLASS, "10", 1),
            Arguments.of(1, CarriageType.SECOND_CLASS, "11", 1),
            Arguments.of(1, CarriageType.SECOND_CLASS, "12", 1)
    );

    @ParameterizedTest
    @VariableSource("arguments")
    public void testInsertAndReadCarriageUniqueData(int carriageId, CarriageType type,
                                                    String carriageNumber, int trainId) {
        Carriage expectedCarriage = new Carriage(carriageId, type, carriageNumber, trainId);
        int id = carriageRepo.create(expectedCarriage);
        Carriage actualCarriage = carriageRepo.read(id);
        assertEquals(expectedCarriage, actualCarriage);
    }


    static Stream<Arguments> arguments1 = Stream.of(
            Arguments.of(1, CarriageType.FIRST_CLASS, "07", 777),
            Arguments.of(1, CarriageType.FIRST_CLASS, null, 1),
            Arguments.of(1, null, "07", 1),
            Arguments.of(1, null, null, 188),
            Arguments.of(1, null, "07", 777),
            Arguments.of(0, null, null, 0)
    );

    @ParameterizedTest
    @VariableSource("arguments1")
    public void testInsertCarriageIfTrainNotExist(int carriageId, CarriageType type,
                                                  String carriageNumber, int trainId) {
        Carriage expectedCarriage = new Carriage(carriageId, type, carriageNumber, trainId);
        assertThrows(DataBaseException.class, () -> carriageRepo.create(expectedCarriage));
    }

    static Stream<Arguments> arguments2 = Stream.of(
            Arguments.of(1, CarriageType.FIRST_CLASS, "07", 1),
            Arguments.of(1, CarriageType.FIRST_CLASS, "08", 1),
            Arguments.of(1, CarriageType.SECOND_CLASS, "09", 1),
            Arguments.of(1, CarriageType.SECOND_CLASS, "10", 1),
            Arguments.of(1, CarriageType.SECOND_CLASS, "11", 1),
            Arguments.of(1, CarriageType.SECOND_CLASS, "12", 1)
    );

    @ParameterizedTest
    @VariableSource("arguments2")
    public void testInsertCarriageNotUnique(int carriageId, CarriageType type,
                                            String carriageNumber, int trainId) throws SQLException {
        int id = carriageRepo.create(new Carriage(carriageId, type, carriageNumber, trainId));
        Carriage expectedCarriage = carriageRepo.read(id);
        assertThrows(DataBaseException.class, () -> carriageRepo.create(expectedCarriage));
    }


    @Test
    public void testRemoveCarriageFromDB() {
        int id = carriageRepo.create(new Carriage(1, CarriageType.FIRST_CLASS, "13", 1));
        assertTrue(carriageRepo.delete(id));
    }


    static Stream<Arguments> input = Stream.of(
            Arguments.of(1, CarriageType.FIRST_CLASS, "07", 1),
            Arguments.of(1, CarriageType.FIRST_CLASS, "08", 2),
            Arguments.of(1, CarriageType.FIRST_CLASS, "09", 3),
            Arguments.of(1, CarriageType.FIRST_CLASS, "11", 2),
            Arguments.of(1, CarriageType.FIRST_CLASS, "15", 3),
            Arguments.of(1, CarriageType.FIRST_CLASS, "12", 1)
    );


    @ParameterizedTest
    @VariableSource("input")
    public void testGetCarriagesByTrainId(int carriageId, CarriageType type,
                                          String carriageNumber, int trainId) {
        Carriage carriage = new Carriage(carriageId, type, carriageNumber, trainId);
        carriages.add(carriage);
        carriages.forEach(carr -> carriageRepo.create(carr));

        assertArrayEquals(carriages.stream()
                        .filter(carr -> carr.getTrainId() == trainId)
                        .sorted(Comparator.comparingInt(Carriage::getTrainId))
                        .toArray(),
                carriageRepo.getCarriagesByTrainId(trainId)
                        .stream()
                        .sorted(Comparator.comparingInt(Carriage::getTrainId))
                        .toArray());

    }

    static Stream<Arguments> inputs = Stream.of(
            Arguments.of(1, CarriageType.FIRST_CLASS, "01", 1),
            Arguments.of(1, CarriageType.FIRST_CLASS, "02", 2),
            Arguments.of(1, CarriageType.FIRST_CLASS, "03", 3),
            Arguments.of(1, CarriageType.FIRST_CLASS, "14", 2),
            Arguments.of(1, CarriageType.FIRST_CLASS, "17", 3),
            Arguments.of(1, CarriageType.FIRST_CLASS, "22", 1)
    );
    @ParameterizedTest
    @VariableSource("inputs")
    public void testGetCarriagesByTrainIdAndType(int carriageId, CarriageType type,
                                                 String carriageNumber, int trainId) {
        Carriage carriage = new Carriage(carriageId, type, carriageNumber, trainId);
        carriages.add(carriage);
        carriages.forEach(carr -> carriageRepo.create(carr));

        assertArrayEquals(carriages.stream()
                        .filter(carr -> carr.getTrainId() == trainId && carr.getType().equals(type))
                        .sorted(Comparator.comparingInt(Carriage::getTrainId))
                        .toArray(),
                carriageRepo.getCarriagesByTrainIdAndType(trainId,type.name())
                        .stream()
                        .sorted(Comparator.comparingInt(Carriage::getTrainId))
                        .toArray());
    }

    @Test
    public void testGetAllCarriageList(){
        carriages.forEach(carr -> carriageRepo.create(carr));
        System.out.println(carriageRepo.getAllCarList());
    }

}
