package com.epam.redkin.railway.model.entity;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Route {
    private int routeId;
    private int trainId;
    private int routeNumber;
    private String routeName;
    private List<Station> stationList;
    private int firstClassFreeSeatsCount;
    private int secondClassFreeSeatsCount;
}
