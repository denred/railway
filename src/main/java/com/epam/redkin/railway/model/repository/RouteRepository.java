package com.epam.redkin.railway.model.repository;

import com.epam.redkin.railway.model.dto.RouteInfoDTO;
import com.epam.redkin.railway.model.dto.StationDTO;
import com.epam.redkin.railway.model.entity.Route;

import java.util.List;
import java.util.Map;

public interface RouteRepository extends EntityDAO<Route> {
    List<RouteInfoDTO> getRouteInfoDTOList();
    RouteInfoDTO getRouteInfoDTOByRouteId(int routsId);
    List<StationDTO> getStationDTOListWithParameters(String departureStation, String arrivalStation);

    List<RouteInfoDTO> getRouteInfoDTOListWithFilter(int offset, int limit, Map<String, String> search);

    int getRouteInfoDTOListCount(Map<String, String> search);
}
