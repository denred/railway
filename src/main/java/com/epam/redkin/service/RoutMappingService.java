package com.epam.redkin.service;

import com.epam.redkin.model.dto.MappingInfoDTO;
import com.epam.redkin.model.entity.RoutePoint;

import java.util.List;

public interface RoutMappingService {

    void updateRoutToStationMapping(RoutePoint routToStationMapping, int stationId);

    void addRoutToStationMapping(RoutePoint routToStationMapping);

    void removeRoutToStationMapping(int routsId, int stationId);


    List<RoutePoint> getAllRoutToStationMappingList();


    List<MappingInfoDTO> getAllRoutToStationMappingListById(int routsId);


    MappingInfoDTO getMappingInfo(int routsId, int stationId);

}
