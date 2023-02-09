package com.epam.redkin.railway.model.service;



import com.epam.redkin.railway.model.dto.RouteInfoDTO;
import com.epam.redkin.railway.model.dto.RouteOrderDTO;
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

    List<RouteOrderDTO> getRouteOrderDtoList(String departureStation, String arrivalStation, LocalDateTime departureDate, String onlyFreeSeats);

    void fillAvailableSeats(List<RouteOrderDTO> routeOrderDTOList);

    LocalDateTime getDepartureDate(String startDate, String startTime);

    List<RouteOrderDTO> getSearchRoutePagination(String departureStation, String arrivalStation, LocalDateTime departureDate, int offset, int limit, String onlyFreeSeats);
}

