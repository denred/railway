package com.epam.redkin.model.repository;

import com.epam.redkin.model.dto.MappingInfoDTO;
import com.epam.redkin.model.entity.RoutePoint;

import java.util.List;

public interface RoutePointRepository {
    RoutePoint getRoutePointById(int id);

    void create(RoutePoint entity);

    List<RoutePoint> getRoutePointList();

    boolean updateRoutePointByStationId(RoutePoint entity, int stationId);

    boolean deleteRoutePointByStationId(int routeId, int stationId);

    MappingInfoDTO getMappingInfo(int routeId, int stationId);

    List<MappingInfoDTO> getMappingInfoListByRouteId(int routeId);
}
