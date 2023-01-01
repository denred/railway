package com.epam.redkin.service.impl;


import com.epam.redkin.model.dto.MappingInfoDTO;
import com.epam.redkin.model.entity.RoutePoint;
import com.epam.redkin.model.repository.RoutePointRepository;
import com.epam.redkin.service.RoutMappingService;

import java.util.List;

public class RoutMappingServiceImpl implements RoutMappingService {
    private RoutePointRepository routMappingRepository;


    public RoutMappingServiceImpl(RoutePointRepository routMappingRepository) {
        this.routMappingRepository = routMappingRepository;
    }


    @Override
    public void updateRoutToStationMapping(RoutePoint routToStationMapping, int stationId) {
        routMappingRepository.updateRoutePointByStationId(routToStationMapping, stationId);
    }

    @Override
    public void addRoutToStationMapping(RoutePoint routToStationMapping) {
            routMappingRepository.create(routToStationMapping);
    }

    @Override
    public void removeRoutToStationMapping(int routsId, int stationId) {
            routMappingRepository.deleteRoutePointByStationId(routsId, stationId);

    }

    @Override
    public MappingInfoDTO getMappingInfo(int routsId, int stationId) {
        return routMappingRepository.getMappingInfo(routsId, stationId);
    }

    @Override
    public List<MappingInfoDTO> getAllRoutToStationMappingListById(int routsId) {
        return routMappingRepository.getMappingInfoListByRouteId(routsId);
    }

    @Override
    public List<RoutePoint> getAllRoutToStationMappingList() {
        return routMappingRepository.getRoutePointList();
    }
}
