package com.epam.redkin.railway.model.repository;

import com.epam.redkin.railway.model.dto.CarriageDTO;
import com.epam.redkin.railway.model.entity.Carriage;
import com.epam.redkin.railway.model.entity.CarriageType;

import java.util.List;
import java.util.Map;

public interface CarriageRepository extends EntityDAO<Carriage> {
    List<Carriage> getCarriagesByTrainId(int trainId);
    List<Carriage> getCarriagesByTrainIdAndType(int trainId, String type);
    List<CarriageDTO> getCarriageDTOList();

    List<CarriageDTO> getCarriageDTOListPagination(int offset, int limit, Map<String, String> search);

    int getCountCarriagesByFilter(Map<String, String> search);

    Carriage getCarriageByNumber(String carriageNumber);

    int getSeatCountByTrainAndCarriageType(int trainId, CarriageType carriageType);

    int getBookedSeatsCount(int trainId, int routeId, CarriageType carriageType);

    List<CarriageDTO> getCarriageDTOsByType(String carriageType);
}
