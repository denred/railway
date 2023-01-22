package com.epam.redkin.railway.model.service;


import com.epam.redkin.railway.model.entity.CarriageType;
import com.epam.redkin.railway.model.entity.Seat;

import java.util.List;

public interface SeatService {


    int getCountSeat(int carId);


    int getCountSeatByCarType(int trainId, CarriageType type);


    List<Seat> getSeatByCarId(int carId);


    List<Seat> getSeatsByIdBatch(List<String> seatsNumber);


    List<String> getSeatsId(String seatNumber);
    List<String> getSeatsIdFromOrder(String seatNumber);
}
