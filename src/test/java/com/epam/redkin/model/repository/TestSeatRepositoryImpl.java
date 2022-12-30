package com.epam.redkin.model.repository;

import com.epam.redkin.model.connectionpool.ConnectionPools;
import com.epam.redkin.model.entity.CarriageType;
import com.epam.redkin.model.entity.Seat;

import com.epam.redkin.model.exception.DataBaseException;
import com.epam.redkin.model.repository.impl.SeatRepositoryImpl;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class TestSeatRepositoryImpl implements TestConstants {
    private static Connection connection;
    private SeatRepository seatRepository;

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
        seatRepository = new SeatRepositoryImpl(ConnectionPools.getTransactional());
        connection.createStatement().executeUpdate(CREATE_TRAIN_TABLE);
        connection.createStatement().executeUpdate(CREATE_CARRIAGE_TABLE);
        connection.createStatement().executeUpdate(CREATE_SEAT_TABLE);
        connection.createStatement().executeUpdate(INSERT_TRAIN1);
        connection.createStatement().executeUpdate(INSERT_TRAIN2);
        connection.createStatement().executeUpdate(INSERT_TRAIN3);
        connection.createStatement().executeUpdate(INSERT_CARRIAGE1);
        connection.createStatement().executeUpdate(INSERT_CARRIAGE2);
        connection.createStatement().executeUpdate(INSERT_CARRIAGE3);
        connection.createStatement().executeUpdate(INSERT_SEAT1);
        connection.createStatement().executeUpdate(INSERT_SEAT2);
        connection.createStatement().executeUpdate(INSERT_SEAT3);
        connection.createStatement().executeUpdate(INSERT_SEAT4);
    }

    @AfterEach
    public void remove() throws SQLException {
        connection.createStatement().executeUpdate(REMOVE_SEAT_TABLE);
        connection.createStatement().executeUpdate(REMOVE_CARRIAGE_TABLE);
        connection.createStatement().executeUpdate(REMOVE_TRAIN_TABLE);
    }

    static Stream<Arguments> arguments = Stream.of(
            Arguments.of(1, "f01", false, 1),
            Arguments.of(2, "s00", true, 2),
            Arguments.of(3, "s02", false, 3),
            Arguments.of(4, "s03", false, 2),
            Arguments.of(5, "s04", false, 2),
            Arguments.of(6, "s05", false, 1),
            Arguments.of(7, "s06", false, 2),
            Arguments.of(8, "s07", false, 1),
            Arguments.of(9, "s802", false, 2),
            Arguments.of(10, "c02", false, 1),
            Arguments.of(11, "s42", false, 1),
            Arguments.of(12, "s62", false, 2),
            Arguments.of(13, "s72", false, 1),
            Arguments.of(14, "s82", false, 2),
            Arguments.of(15, "s92", false, 1),
            Arguments.of(16, "s102", false, 1),
            Arguments.of(17, "s202", false, 2),
            Arguments.of(18, "s302", false, 3),
            Arguments.of(19, "s502", false, 2),
            Arguments.of(20, "s021", false, 1),
            Arguments.of(21, "s0211", false, 2),
            Arguments.of(22, "s0442", false, 1)
    );

    @ParameterizedTest
    @VariableSource("arguments")
    void testCreateAndReadSeat(int id, String seatNumber, boolean busy, int carriageId) {
        Seat actual = new Seat(id, carriageId, seatNumber, busy);
        Seat expected = seatRepository.read(seatRepository.create(actual));
        assertEquals(expected, actual);
    }

    @Test
    void testCreateSeatWithNullNumber() {
        assertThrows(DataBaseException.class, () -> seatRepository.create(new Seat(1, 1, null, false)));
    }

    @Test
    void testCreateTrainWithDuplicateNumber() {
        assertThrows(DataBaseException.class, () -> seatRepository.create(new Seat(100, 1, "1055", true)));
        assertThrows(DataBaseException.class, () -> seatRepository.create(new Seat(101, 1, "1055", false)));
    }

    @Test
    void testUpdateSeat() {
        int id = 1;
        Seat seat = seatRepository.read(id);
        seat.setSeatNumber(seat.getSeatNumber() + id);
        seatRepository.update(seat);
        assertEquals(seatRepository.read(id), seat);
    }

    @Test
    void testUpdateExistSeat() {
        Seat seat = seatRepository.read(1);
        seat.setId(3);
        assertThrows(DataBaseException.class, () -> seatRepository.update(seat));
    }

    @ParameterizedTest
    @CsvSource({"1,1", "2,2", "3,1"})
    void testGetCountSeatsByCarriageId(int expected, int actual) {
        assertEquals(seatRepository.getSeatsCountByCarriageId(expected), actual);
    }

    @ParameterizedTest
    @CsvSource({"1,0", "2,0", "3,0"})
    void testDeleteAllSeatsByCarriageId(int expected, int actual) {
        assertTrue(seatRepository.deleteAllSeatsByCarriageId(expected));
        assertEquals(seatRepository.getSeatsCountByCarriageId(expected), actual);
    }

    @Test
    void testGetSeatsCountByTrainIdAndByTypes() {
        assertEquals(seatRepository.getSeatsCountByTrainIdAndByTypes(3, CarriageType.FIRST_CLASS), 4);
    }

    @ParameterizedTest
    @CsvSource({"1,1", "2,2", "3,1"})
    void getListSeatsByCarriageId(int expected, int actual) {
        assertEquals(seatRepository.getListSeatsByCarriageId(expected).size(), actual);
    }

    @Test
    void testGetListSeatsByIdBatch() {
        List<Seat> actual = Stream.of(seatRepository.getListSeatsByCarriageId(1),
                        seatRepository.getListSeatsByCarriageId(2),
                        seatRepository.getListSeatsByCarriageId(3))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        List<Seat> expected = seatRepository.getListSeatsByIdBatch(List.of("1", "2", "3", "4"));

        assertEquals(expected.size(), actual.size());
        assertArrayEquals(expected.stream()
                        .sorted(Comparator.comparingInt(Seat::getId))
                        .toArray(),
                actual.stream()
                        .sorted(Comparator.comparingInt(Seat::getId))
                        .toArray());
    }

    @Test
    void testReservedAndClearSeat() {
        List<Seat> seats = seatRepository.getListSeatsByIdBatch(List.of("1", "2", "3", "4"));
        seats.forEach(seat -> {
            seatRepository.reservedSeat(seat.getId());
            assertTrue(seatRepository.read(seat.getId()).isBusy());
            seatRepository.clearSeat(seat.getId());
            assertFalse(seatRepository.read(seat.getId()).isBusy());
        });
    }




}
