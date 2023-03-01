package com.epam.redkin.railway.model.repository;

import com.epam.redkin.railway.model.dto.SeatDTO;
import com.epam.redkin.railway.model.entity.CarriageType;
import com.epam.redkin.railway.model.entity.Seat;

import java.util.List;

public interface SeatRepository extends EntityDAO<Seat> {
    boolean deleteAllSeatsByCarriageId(int carId);
    int getSeatsCountByCarriageId(int carriageId);
    int getBusySeatsCountByCarriageId(int carriageId);
    int getSeatsCountByTrainIdAndByTypes(int trainId, CarriageType type);
    List<Seat> getListSeatsByCarriageId(int carriageId);
    List<Seat> getListSeatsByIdBatch(List<String> seatsNumber);
    void reservedSeat(int seatId);
    void clearSeat(int seatId);
    boolean isReservationExists(int seatId, int stationId, int routeId, int trainId);
    List<Seat> getSeatsByTrainIdAndType(int trainId, CarriageType carriageType);
}
