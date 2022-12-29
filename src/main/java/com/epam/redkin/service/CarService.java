package com.epam.redkin.service;


import com.epam.redkin.model.dto.CarDto;
import com.epam.redkin.model.entity.Carriage;

import java.util.List;

public interface CarService {

    List<CarDto> getAllCarList();


    void removeCar(int carId);


    List<Carriage> getCarByTrainId(int trainId);


    void addCar(CarDto carDto);


    Carriage getCarById(int carId);


    void updateCar(CarDto carDto);


    List<Carriage> getCarByTrainIdAndCarType(int trainId, String carType);

}
