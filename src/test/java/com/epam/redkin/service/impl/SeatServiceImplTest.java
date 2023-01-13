package com.epam.redkin.service.impl;

import com.epam.redkin.model.repository.VariableSource;
import com.epam.redkin.service.SeatService;
import com.epam.redkin.web.listener.AppContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class SeatServiceImplTest {

    @Test
    void getCountSeat() {
    }

    @Test
    void getCountSeatByCarType() {
    }

    @Test
    void getSeatByCarId() {
    }

    @Test
    void getSeatsByIdBatch() {
    }






    static Stream<Arguments> stringIntAndListProvider() {
        return Stream.of(
                arguments("[1,2,3,4,5,6,7,8,9,10,11]", List.of("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11")),
                arguments("[0,2,0,4,0,0,0,0,0,0,0]", List.of("0", "2", "0", "4", "0", "0", "0", "0", "0", "0", "0")),
                arguments("[1,2,3]  ", List.of("1", "2", "3")),
                arguments("[[1   ]]", List.of("1")),
                arguments("[1,2,3] ", List.of("1", "2", "3")),
                arguments("[1,2,3,4 ]", List.of("1", "2", "3", "4")),
                arguments("[1,2,3,4,5 ]]", List.of("1", "2", "3", "4", "5"))
        );
    }

    @ParameterizedTest
    @MethodSource("stringIntAndListProvider")
    void getSeatsId(String input, List<String> expected) {
        SeatService seatService = AppContext.getInstance().getSeatService();
        List<String> actual = seatService.getSeatsId(input);
        assertArrayEquals(expected.toArray(), actual.toArray());
    }
}