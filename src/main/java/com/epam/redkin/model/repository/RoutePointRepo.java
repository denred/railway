package com.epam.redkin.model.repository;

import com.epam.redkin.model.dto.MappingInfoDto;
import com.epam.redkin.model.entity.RoutePoint;

import java.util.List;

public interface RoutePointRepo {
    List<RoutePoint> getAllRoutToStationMappingList();
    void create(RoutePoint entity);

    RoutePoint read(int id);

    boolean update(RoutePoint entity, int stationId);

    boolean delete(int routsId, int stationId);

    List<MappingInfoDto> getAllRoutToStationMappingListById(int routsId);
    MappingInfoDto getMappingInfo(int routsId, int stationId);
}
