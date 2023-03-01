package com.epam.redkin.railway.model.service;


import com.epam.redkin.railway.model.entity.CarriageType;
import com.epam.redkin.railway.model.entity.Seat;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Collection;
import java.util.List;

public interface SeatService {


    int getCountSeat(int carId);


    int getCountSeatByCarType(int trainId, CarriageType type);


    List<Seat> getCarriageSeats(int carId);


    List<Seat> getSeatsByIdBatch(List<String> seatsNumber);


    List<String> getSeatsId(String seatNumber);
    List<String> getSeatsIdFromOrder(String seatNumber);

    List<Seat> fillReservedSeats(List<Seat> seat, String routeId, String departureStationId, String arrivalStationId, String trainId);


    List<Seat> getSeatsByTrainIdAndCarriageType(int trainId, CarriageType carriageType);
}
