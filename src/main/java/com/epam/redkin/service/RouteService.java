package com.epam.redkin.service;



import com.epam.redkin.model.dto.RouteInfoDTO;
import com.epam.redkin.model.dto.RoutsOrderDto;
import com.epam.redkin.model.entity.Route;

import java.time.LocalDateTime;
import java.util.List;

public interface RouteService {


    void addRout(Route route);

    void removeRout(int routsId);


    void updateRout(Route route);

    List<RouteInfoDTO> getAllRouteList();
    List<RouteInfoDTO> getRouteListByCurrentRecordAndRecordsPerPage(int currentPage, int recordsPerPage);
    int getRouteListSize();


    RouteInfoDTO getRoutById(int routsId);

    List<RoutsOrderDto> getRouteListWithParameters(String departureStation, String arrivalStation, LocalDateTime departureDate);
}

