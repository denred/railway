package com.epam.redkin.model.repository;

import com.epam.redkin.model.dto.CarDto;
import com.epam.redkin.model.entity.Carriage;

import java.util.List;

public interface CarriageRepo extends EntityDAO<Carriage> {
    List<Carriage> getCarriagesByTrainId(int trainId);
    List<Carriage> getCarriagesByTrainIdAndType(int trainId, String type);
    List<CarDto> getAllCarList();
}
