package com.epam.redkin.railway.model.service;


import com.epam.redkin.railway.model.dto.CarriageDTO;
import com.epam.redkin.railway.model.entity.Carriage;
import com.epam.redkin.railway.model.entity.CarriageType;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface CarriageService {

    List<CarriageDTO> getCarriageDTOList();


    void removeCarriage(int carId);


    List<Carriage> getCarriageByTrainId(int trainId);


    void addCarriage(CarriageDTO carriageDTO);


    Carriage getCarriageById(int carriageId);


    void updateCarriage(CarriageDTO carriageDTO);


    List<Carriage> getCarriageByTrainIdAndCarriageType(int trainId, String carType);

    List<CarriageDTO> getCarriageDtoListPagination(int offset, int limit, Map<String, String> search);

    int getRouteListSize(Map<String, String> search);

    Carriage getCarriageByNumber(String carriageNumber);

    int getSeatCountByCarriageType(int trainId, CarriageType carriageType);

    int getBookedSeatsCountByCarriageType(int routeId, int trainId, CarriageType carriageType);

    Set<CarriageType> getCarriageTypesByTrainId(int trainId);

    List<CarriageDTO> getCarriageDTOsByCarriageType(String carriageType);
}
