package com.epam.redkin.railway.model.repository;

import com.epam.redkin.railway.model.dto.MappingInfoDTO;
import com.epam.redkin.railway.model.entity.RoutePoint;

import java.util.List;

public interface RoutePointRepository {
    List<RoutePoint> getRoutePointListByRouteId(int id);

    void create(RoutePoint entity);

    List<RoutePoint> getRoutePointList();

    boolean updateRoutePointByStationId(RoutePoint entity, int stationId);

    boolean deleteRoutePointByStationId(int routeId, int stationId);

    MappingInfoDTO getMappingInfo(int routeId, int stationId);

    List<MappingInfoDTO> getMappingInfoListByRouteId(int routeId);
}
