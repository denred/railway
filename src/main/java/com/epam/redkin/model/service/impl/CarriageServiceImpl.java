package com.epam.redkin.model.service.impl;


import com.epam.redkin.model.dto.CarriageDTO;
import com.epam.redkin.model.entity.Carriage;
import com.epam.redkin.model.entity.Seat;
import com.epam.redkin.model.exception.IncorrectDataException;
import com.epam.redkin.model.repository.CarriageRepository;
import com.epam.redkin.model.repository.SeatRepository;
import com.epam.redkin.model.service.CarriageService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;


@SuppressWarnings({"ALL", "FieldMayBeFinal"})
public class CarriageServiceImpl implements CarriageService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CarriageServiceImpl.class);
    private CarriageRepository carriageRepository;
    private SeatRepository seatRepository;

    public CarriageServiceImpl(CarriageRepository carriageRepository, SeatRepository seatRepository) {
        this.carriageRepository = carriageRepository;
        this.seatRepository = seatRepository;
    }

    @Override
    public void updateCar(CarriageDTO carriageDTO) {
        Carriage car = getCarFromCarDto(carriageDTO);
        try {
            carriageRepository.update(car);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        int countSeatBusy = seatRepository.getBusySeatsCountByCarriageId(carriageDTO.getCarId());
        int countSeat = seatRepository.getSeatsCountByCarriageId(carriageDTO.getCarId());
        if (countSeatBusy == 0) {
            if (countSeat > carriageDTO.getSeats()) {
                seatRepository.deleteAllSeatsByCarriageId(carriageDTO.getCarId());
                for (int i = 1; i <= carriageDTO.getSeats(); i++) {
                    Seat seat = getSeatFromCarDto(carriageDTO, String.valueOf(i));
                    try {
                        seatRepository.create(seat);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            if (countSeat < carriageDTO.getSeats()) {
                for (int i = countSeat + 1; i <= carriageDTO.getSeats(); i++) {
                    Seat seat = getSeatFromCarDto(carriageDTO, String.valueOf(i));
                    LOGGER.debug("1" + seat.getSeatNumber() + " - " + seat.getCarriageId());
                    try {
                        seatRepository.create(seat);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        } else {
            IncorrectDataException e = new IncorrectDataException("Can`t edit car because there are ordered seats");
            LOGGER.error(e.getMessage());
            throw e;
        }
    }

    @Override
    public Carriage getCarById(int carId) {
        return carriageRepository.read(carId);
    }

    @Override
    public List<Carriage> getCarByTrainId(int trainId) {
        return carriageRepository.getCarriagesByTrainId(trainId);
    }

    @Override
    public void addCarriage(CarriageDTO carriageDTO) {
        Carriage carriage = getCarFromCarDto(carriageDTO);
        int carriageId = carriage.getCarriageId();
        if (carriageRepository.read(carriageId) == null) {
            try {
                carriageId = carriageRepository.create(carriage);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        carriageDTO.setCarId(carriageId);
        for (int i = 1; i <= carriageDTO.getSeats(); i++) {
            Seat seat = getSeatFromCarDto(carriageDTO, String.valueOf(i));
            try {
                seatRepository.create(seat);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public List<Carriage> getCarByTrainIdAndCarType(int trainId, String carType) {
        return carriageRepository.getCarriagesByTrainIdAndType(trainId, carType);
    }

    @Override
    public List<CarriageDTO> getCarriageDtoListByCurrentRecordAndRecordsPerPage(int currentPage, int recordsPerPage, String trainFilter, String carriageTypeFilter) {
        List<CarriageDTO> allRecords = getAllCarriageDTOList();
        if(!StringUtils.isAllBlank(trainFilter, carriageTypeFilter)) {
            allRecords = getAllCarriageDTOList().stream()
                    .filter(dto -> StringUtils.isBlank(trainFilter)?true:dto.getTrainNumber().toLowerCase().contains(trainFilter.toLowerCase()))
                    .filter(dto -> StringUtils.isBlank(carriageTypeFilter)?true:dto.getCarriageType().name().toLowerCase().contains(carriageTypeFilter.toLowerCase()))
                    .collect(Collectors.toList());
        }
        return allRecords.subList(currentPage, Math.min(recordsPerPage, allRecords.size()));
    }

    @Override
    public int getRouteListSize() {
        return carriageRepository.getAllCarriageDTOList().size();
    }

    @Override
    public void removeCar(int carId) {
        seatRepository.deleteAllSeatsByCarriageId(carId);
        carriageRepository.delete(carId);
    }

    @Override
    public List<CarriageDTO> getAllCarriageDTOList() {
        List<CarriageDTO> result = carriageRepository.getAllCarriageDTOList();
        for (CarriageDTO car : result) {
            int seat = seatRepository.getSeatsCountByCarriageId(car.getCarId());
            car.setSeats(seat);
            calculatePrice(car);
        }
        return result;
    }

    private Seat getSeatFromCarDto(CarriageDTO carriageDTO, String seatNumber) {
        Seat seat = new Seat();
        seat.setCarriageId(carriageDTO.getCarId());
        seat.setSeatNumber(seatNumber);
        seat.setBusy(false);
        return seat;
    }

    private Carriage getCarFromCarDto(CarriageDTO carriageDTO) {
        Carriage car = new Carriage();
        car.setCarriageId(carriageDTO.getCarId());
        car.setType(carriageDTO.getCarriageType());
        car.setNumber(carriageDTO.getCarNumber());
        car.setTrainId(carriageDTO.getTrainId());
        return car;
    }

    private void calculatePrice(CarriageDTO car) {
        car.setPrice(car.getCarriageType().getPrice());
    }

}
