package com.epam.redkin.service.impl;


import com.epam.redkin.model.dto.CarDto;
import com.epam.redkin.model.entity.Carriage;
import com.epam.redkin.model.entity.Seat;
import com.epam.redkin.model.exception.IncorrectDataException;
import com.epam.redkin.model.repository.CarriageRepo;
import com.epam.redkin.model.repository.SeatRepo;
import com.epam.redkin.service.CarService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@SuppressWarnings({"ALL", "FieldMayBeFinal"})
public class CarServiceImpl implements CarService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CarServiceImpl.class);

    private CarriageRepo carRepository;
    private SeatRepo seatRepository;

    public CarServiceImpl(CarriageRepo carRepository, SeatRepo seatRepository) {
        this.carRepository = carRepository;
        this.seatRepository = seatRepository;
    }

    @Override
    public void updateCar(CarDto carDto) {
        Carriage car = getCarFromCarDto(carDto);
        carRepository.update(car);
        int countSeatBusy = seatRepository.getSeatsCarriageBusy(carDto.getCarId());
        int countSeat = seatRepository.getSeatsCarriage(carDto.getCarId());
        if (countSeatBusy == 0) {
            if (countSeat > carDto.getSeats()) {
                seatRepository.deleteAllSeatsByCarId(carDto.getCarId());
                for (int i = 1; i <= carDto.getSeats(); i++) {
                    Seat seat = getSeatFromCarDto(carDto, String.valueOf(i));
                    seatRepository.create(seat);
                }
            }
            if (countSeat < carDto.getSeats()) {
                for (int i = countSeat + 1; i <= carDto.getSeats(); i++) {
                    Seat seat = getSeatFromCarDto(carDto, String.valueOf(i));
                    seatRepository.create(seat);
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
        return carRepository.read(carId);
    }

    @Override
    public List<Carriage> getCarByTrainId(int trainId) {
        return carRepository.getCarriagesByTrainId(trainId);
    }

    @Override
    public void addCar(CarDto carDto) {
        Carriage car = getCarFromCarDto(carDto);
        int carId = carRepository.create(car);
        carDto.setCarId(carId);
        for (int i = 1; i <= carDto.getSeats(); i++) {
            Seat seat = getSeatFromCarDto(carDto, String.valueOf(i));
            seatRepository.create(seat);
        }
    }

    @Override
    public List<Carriage> getCarByTrainIdAndCarType(int trainId, String carType) {
        return carRepository.getCarriagesByTrainIdAndType(trainId, carType);
    }

    @Override
    public void removeCar(int carId) {
        seatRepository.deleteAllSeatsByCarId(carId);
        carRepository.delete(carId);
    }

    @Override
    public List<CarDto> getAllCarList() {
        List<CarDto> result = carRepository.getAllCarList();
        for (CarDto car : result) {
            int seat = seatRepository.getSeatsCarriage(car.getCarId());
            car.setSeats(seat);
            calculatePrice(car);
        }
        return result;
    }

    private Seat getSeatFromCarDto(CarDto carDto, String seatNumber) {
        Seat seat = new Seat();
        seat.setId(carDto.getCarId());
        seat.setSeatNumber(seatNumber);
        seat.setBusy(false);
        return seat;
    }

    private Carriage getCarFromCarDto(CarDto carDto) {
        Carriage car = new Carriage();
        car.setCarriageId(carDto.getCarId());
        car.setType(carDto.getCarType());
        car.setNumber(carDto.getCarNumber());
        car.setTrainId(carDto.getTrainId());
        return car;
    }

    private void calculatePrice(CarDto car) {
        car.setPrice(car.getCarType().getPrice());
    }

}
