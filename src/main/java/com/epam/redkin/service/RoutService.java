package com.epam.redkin.service;



import com.epam.redkin.model.dto.RoutInfoDto;
import com.epam.redkin.model.dto.RoutsOrderDto;
import com.epam.redkin.model.entity.Route;

import java.time.LocalDateTime;
import java.util.List;

public interface RoutService {


    void addRout(Route rout);

    void removeRout(int routsId);


    void updateRout(Route rout);

    List<RoutInfoDto> getAllRoutList();


    RoutInfoDto getRoutById(int routsId);

    List<RoutsOrderDto> getRouteListWithParameters(String departureStation, String arrivalStation, LocalDateTime departureDate);
}

