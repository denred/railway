package com.epam.redkin.railway.model.service;


import com.epam.redkin.railway.model.dto.CarriageDTO;
import com.epam.redkin.railway.model.entity.Carriage;

import java.util.List;

public interface CarriageService {

    List<CarriageDTO> getCarriageDTOList();


    void removeCarriage(int carId);


    List<Carriage> getCarriageByTrainId(int trainId);


    void addCarriage(CarriageDTO carriageDTO);


    Carriage getCarriageById(int carriageId);


    void updateCarriage(CarriageDTO carriageDTO);


    List<Carriage> getCarriageByTrainIdAndCarriageType(int trainId, String carType);

    List<CarriageDTO> getCarriageDtoListByCurrentRecordAndRecordsPerPage(int i, int i1, String filter, String filterValue);

    int getRouteListSize();
}
