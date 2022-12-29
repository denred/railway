package com.epam.redkin.model.repository;

import com.epam.redkin.model.entity.CarriageType;
import com.epam.redkin.model.entity.Seat;

import java.util.List;

public interface SeatRepo extends EntityDAO<Seat> {
    boolean deleteAllSeatsByCarId(int carId);
    int getSeatsCarriage(int carriageId);
    int getSeatsCarriageBusy(int carriageId);
    int getSeatsTrainByTypes(int trainId, CarriageType type);
    List<Seat> getListSeatsByCarrId(int carriageId);
    List<Seat> getListSeatsByIdBatch(List<String> seatsNumber);
    void takeSeat(int seatId);
    void takeOffSeat(int seatId);
}
