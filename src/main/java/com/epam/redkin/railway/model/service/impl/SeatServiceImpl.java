package com.epam.redkin.railway.model.service.impl;


import com.epam.redkin.railway.appcontext.AppContext;
import com.epam.redkin.railway.model.dto.MappingInfoDTO;
import com.epam.redkin.railway.model.entity.CarriageType;
import com.epam.redkin.railway.model.entity.Seat;
import com.epam.redkin.railway.model.exception.ForbiddenException;
import com.epam.redkin.railway.model.service.RouteMappingService;
import com.epam.redkin.railway.model.service.SeatService;
import com.epam.redkin.railway.model.repository.SeatRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;


public class SeatServiceImpl implements SeatService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SeatServiceImpl.class);
    private final SeatRepository seatRepository;

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
    public List<Seat> getCarriageSeats(int carId) {
        return seatRepository.getListSeatsByCarriageId(carId);
    }

    @Override
    public List<Seat> getSeatsByIdBatch(List<String> seatsNumber) {
        LOGGER.info("Started --> public List<Seat> getSeatsByIdBatch(List<String> seatsNumber) " +
                "--> seatsNumber= " + seatsNumber);
        List<Seat> seats = seatRepository.getListSeatsByIdBatch(seatsNumber);
        seats.forEach(seat -> {
            if (seat.isBusy()) {
                LOGGER.error("Seat " + seat.getSeatNumber() + " is busy");
                throw new ForbiddenException("Seat is busy: " + seat.getSeatNumber());
            }
        });
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

    @Override
    public List<Seat> fillReservedSeats(List<Seat> seatList, String routeId, String departureStationId, String arrivalStationId, String trainId) {
        LOGGER.info("Started fillReservedSeats(routeId={}, departureStationId={}, arrivalStationId={}, trainId={})", routeId, departureStationId, arrivalStationId, trainId);
        RouteMappingService routeMappingService = AppContext.getInstance().getRouteMappingService();
        int routeIdInt = Integer.parseInt(routeId);
        int departureStationIdInt = Integer.parseInt(departureStationId);
        int arrivalStationIdInt = Integer.parseInt(arrivalStationId);
        List<MappingInfoDTO> mappingInfoDTOs = routeMappingService.getRouteStations(routeIdInt, departureStationIdInt, arrivalStationIdInt);
        List<Seat> newSeatList = new ArrayList<>();
        seatList.forEach(seat -> {
            AtomicBoolean state = new AtomicBoolean(true);
            int seatId = seat.getId();
            int trainIdInt = Integer.parseInt(trainId);
            if (mappingInfoDTOs.size() == 2) {
                state.set(!mappingInfoDTOs.stream().allMatch(route -> seatRepository.isReservationExists(seatId, Integer.parseInt(route.getStationId()), Integer.parseInt(route.getRoutsId()), trainIdInt)));
            } else {
                for (int i = 1; i < mappingInfoDTOs.size() - 1; i++) {

                    int stationId = Integer.parseInt(mappingInfoDTOs.get(i).getStationId());
                    if (seatRepository.isReservationExists(seatId, stationId, routeIdInt, trainIdInt)) {
                        state.set(false);
                    }
                }
            }
            if (state.get()) {
                newSeatList.add(seat);
            }
        });
        LOGGER.info("List of available seats following a check for availability " + newSeatList);
        return newSeatList;
    }

    @Override
    public List<Seat> getSeatsByTrainIdAndCarriageType(int trainId, CarriageType carriageType) {
        return seatRepository.getSeatsByTrainIdAndType(trainId, carriageType);
    }

}
