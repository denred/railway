package com.epam.redkin.model.repository;

import com.epam.redkin.model.dto.RoutInfoDto;
import com.epam.redkin.model.dto.StationDto;
import com.epam.redkin.model.entity.Route;

import java.util.List;

public interface RouteRepo extends EntityDAO<Route> {
    List<RoutInfoDto> getAllRoutList();
    RoutInfoDto getRoutById(int routsId);
    List<StationDto> getRouteListWithParameters(String departureStation, String arrivalStation);
}
