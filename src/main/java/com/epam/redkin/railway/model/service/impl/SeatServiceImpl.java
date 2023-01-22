package com.epam.redkin.railway.model.service.impl;


import com.epam.redkin.railway.model.entity.CarriageType;
import com.epam.redkin.railway.model.entity.Seat;
import com.epam.redkin.railway.model.service.SeatService;
import com.epam.redkin.railway.model.repository.SeatRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SeatServiceImpl implements SeatService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SeatServiceImpl.class);
    private SeatRepository seatRepository;

    public SeatServiceImpl(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }

    @Override
    public int getCountSeat(int carId) {
        return seatRepository.getSeatsCountByCarriageId(carId);
    }

    @Override
    public int getCountSeatByCarType(int trainId, CarriageType type) {
        return seatRepository.getSeatsCountByTrainIdAndByTypes(trainId, type);
    }

    @Override
    public List<Seat> getSeatByCarId(int carId) {
        return seatRepository.getListSeatsByCarriageId(carId);
    }

    @Override
    public List<Seat> getSeatsByIdBatch(List<String> seatsNumber) {
        return seatRepository.getListSeatsByIdBatch(seatsNumber);
    }

    @Override
    public List<String> getSeatsId(String seatsId) {
        return Arrays.stream(seatsId.replaceAll("\\[", "")
                        .replaceAll("\\]", "")
                        .trim()
                        .split(","))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getSeatsIdFromOrder(String seatsId) {
        return Arrays.stream(seatsId.replaceAll("\\[", "")
                        .replaceAll("\\]", "")
                        .trim()
                        .split(" "))
                .collect(Collectors.toList());
    }
}
