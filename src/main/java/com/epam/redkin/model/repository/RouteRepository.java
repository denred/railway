package com.epam.redkin.model.repository;

import com.epam.redkin.model.dto.RouteInfoDTO;
import com.epam.redkin.model.dto.StationDTO;
import com.epam.redkin.model.entity.Route;

import java.util.List;

public interface RouteRepository extends EntityDAO<Route> {
    List<RouteInfoDTO> getAllRouteInfoDTOList();
    RouteInfoDTO getRouteInfoDTOByRouteId(int routsId);
    List<StationDTO> getStationDTOListWithParameters(String departureStation, String arrivalStation);
}
