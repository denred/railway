package com.epam.redkin.model.service;


import com.epam.redkin.model.dto.CarriageDTO;
import com.epam.redkin.model.entity.Carriage;

import java.util.List;

public interface CarriageService {

    List<CarriageDTO> getAllCarriageDTOList();


    void removeCar(int carId);


    List<Carriage> getCarByTrainId(int trainId);


    void addCarriage(CarriageDTO carriageDTO);


    Carriage getCarById(int carId);


    void updateCar(CarriageDTO carriageDTO);


    List<Carriage> getCarByTrainIdAndCarType(int trainId, String carType);

    List<CarriageDTO> getCarriageDtoListByCurrentRecordAndRecordsPerPage(int i, int i1, String filter, String filterValue);

    int getRouteListSize();
}
