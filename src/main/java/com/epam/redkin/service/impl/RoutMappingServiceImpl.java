package com.epam.redkin.service.impl;


import com.epam.redkin.model.dto.MappingInfoDto;
import com.epam.redkin.model.entity.RoutePoint;
import com.epam.redkin.model.repository.RoutePointRepo;
import com.epam.redkin.service.RoutMappingService;

import java.util.List;

public class RoutMappingServiceImpl implements RoutMappingService {
    private RoutePointRepo routMappingRepository;


    public RoutMappingServiceImpl(RoutePointRepo routMappingRepository) {
        this.routMappingRepository = routMappingRepository;
    }


    @Override
    public void updateRoutToStationMapping(RoutePoint routToStationMapping, int stationId) {
        routMappingRepository.update(routToStationMapping, stationId);
    }

    @Override
    public void addRoutToStationMapping(RoutePoint routToStationMapping) {
            routMappingRepository.create(routToStationMapping);
    }

    @Override
    public void removeRoutToStationMapping(int routsId, int stationId) {
            routMappingRepository.delete(routsId, stationId);

    }

    @Override
    public MappingInfoDto getMappingInfo(int routsId, int stationId) {
        return routMappingRepository.getMappingInfo(routsId, stationId);
    }

    @Override
    public List<MappingInfoDto> getAllRoutToStationMappingListById(int routsId) {
        return routMappingRepository.getAllRoutToStationMappingListById(routsId);
    }

    @Override
    public List<RoutePoint> getAllRoutToStationMappingList() {
        return routMappingRepository.getAllRoutToStationMappingList();
    }
}
