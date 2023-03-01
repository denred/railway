package com.epam.redkin.railway.model.service;

import com.epam.redkin.railway.model.dto.MappingInfoDTO;
import com.epam.redkin.railway.model.entity.RoutePoint;

import java.util.List;

public interface RouteMappingService {

    void updateRoutToStationMapping(RoutePoint routToStationMapping, int stationId);

    void addRoutToStationMapping(RoutePoint routToStationMapping);

    void removeRoutToStationMapping(int routsId, int stationId);


    List<RoutePoint> getRouteMappingList();


    List<MappingInfoDTO> getRouteMappingInfoDTOs(int routeId);


    MappingInfoDTO getMappingInfo(int routeId, int stationId);

    int getLastStation(List<MappingInfoDTO> mappingInfo);

    List<MappingInfoDTO> getMappingInfoDtoListByRouteIdAndPagination(int routeId, int offset, int limit);

    int getRouteStationsCount(int routeId);

    List<MappingInfoDTO> getRouteStations(int routeId, int departureStationId, int arrivalStationId);
}
