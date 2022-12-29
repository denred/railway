package com.epam.redkin.service.impl;


import com.epam.redkin.model.entity.CarriageType;
import com.epam.redkin.model.entity.Seat;
import com.epam.redkin.model.repository.SeatRepo;
import com.epam.redkin.service.SeatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SeatServiceImpl implements SeatService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SeatServiceImpl.class);
    private static final String UUID = "([0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12})";
    private SeatRepo seatRepository;

    public SeatServiceImpl(SeatRepo seatRepository) {
        this.seatRepository = seatRepository;
    }

    @Override
    public int getCountSeat(int carId) {
        return seatRepository.getSeatsCarriage(carId);
    }

    @Override
    public int getCountSeatByCarType(int trainId, CarriageType type) {
        return seatRepository.getSeatsTrainByTypes(trainId, type);
    }

    @Override
    public List<Seat> getSeatByCarId(int carId) {
        return seatRepository.getListSeatsByCarrId(carId);
    }

    @Override
    public List<Seat> getSeatsByIdBatch(List<String> seatsNumber) {
        return seatRepository.getListSeatsByIdBatch(seatsNumber);
    }

    @Override
    public ArrayList<String> getSeatsId(String seatId) {
        ArrayList<String> seatIdList = new ArrayList<>();
        Matcher m = Pattern.compile(UUID).matcher(seatId);
        while (m.find()) {
            seatIdList.add(m.group(1));
        }
        return seatIdList;
    }
}
