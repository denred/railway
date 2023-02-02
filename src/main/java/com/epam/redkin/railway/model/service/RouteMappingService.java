package com.epam.redkin.railway.model.service;

import com.epam.redkin.railway.model.dto.MappingInfoDTO;
import com.epam.redkin.railway.model.entity.RoutePoint;

import java.util.List;

public interface RouteMappingService {

    void updateRoutToStationMapping(RoutePoint routToStationMapping, int stationId);

    void addRoutToStationMapping(RoutePoint routToStationMapping);

    void removeRoutToStationMapping(int routsId, int stationId);


    List<RoutePoint> getAllRoutToStationMappingList();


    List<MappingInfoDTO> getMappingInfoDtoListByRouteId(int routsId);


    MappingInfoDTO getMappingInfo(int routsId, int stationId);

    int getLastStation(List<MappingInfoDTO> mappingInfo);
}
