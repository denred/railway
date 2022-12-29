package com.epam.redkin.model.repository.impl;

import com.epam.redkin.model.entity.Seat;

import java.util.List;

public class TestDBgetListSeatsByIdBatch {
    public static void main(String[] args) {
        SeatRepoImpl sr = new SeatRepoImpl();
        List<Seat> listSeatsByIdBatch = sr.getListSeatsByIdBatch(List.of("1","2", "4","8"));
        System.out.println(listSeatsByIdBatch.toString());
    }
}
