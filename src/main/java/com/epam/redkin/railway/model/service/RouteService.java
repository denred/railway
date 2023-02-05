package com.epam.redkin.railway.model.service;



import com.epam.redkin.railway.model.dto.RouteInfoDTO;
import com.epam.redkin.railway.model.dto.RoutsOrderDTO;
import com.epam.redkin.railway.model.entity.Route;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface RouteService {


    void addRoute(Route route);

    void removeRout(int routsId);


    void updateRoute(Route route);

    List<RouteInfoDTO> getAllRouteList();
    List<RouteInfoDTO> getRouteInfoListWithFilter(int offset, int limit, Map<String, String> search);
    int getRouteListSize(Map<String, String> search);


    RouteInfoDTO getRouteInfoById(int routsId);

    List<RoutsOrderDTO> getRouteListWithParameters(String departureStation, String arrivalStation, LocalDateTime departureDate);

    void fillAvailableSeats(List<RoutsOrderDTO> routeOrderDTOList);

    LocalDateTime getDepartureDate(String startDate, String startTime);
}

