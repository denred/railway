package com.epam.redkin.service;

import com.epam.redkin.model.dto.MappingInfoDto;
import com.epam.redkin.model.entity.RoutePoint;

import java.util.List;

public interface RoutMappingService {

    void updateRoutToStationMapping(RoutePoint routToStationMapping, int stationId);

    void addRoutToStationMapping(RoutePoint routToStationMapping);

    void removeRoutToStationMapping(int routsId, int stationId);


    List<RoutePoint> getAllRoutToStationMappingList();


    List<MappingInfoDto> getAllRoutToStationMappingListById(int routsId);


    MappingInfoDto getMappingInfo(int routsId, int stationId);

}
