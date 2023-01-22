package com.epam.redkin.railway.model.repository;

import com.epam.redkin.railway.model.dto.CarriageDTO;
import com.epam.redkin.railway.model.entity.Carriage;

import java.util.List;

public interface CarriageRepository extends EntityDAO<Carriage> {
    List<Carriage> getCarriagesByTrainId(int trainId);
    List<Carriage> getCarriagesByTrainIdAndType(int trainId, String type);
    List<CarriageDTO> getAllCarriageDTOList();
}
