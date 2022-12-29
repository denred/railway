package com.epam.redkin.model.entity;

import java.util.List;

public class Route {
    private int routeId;
    private int trainId;
    private int routeNumber;
    private String routeName;
    private List<Station> stationList;
    private int firstClassFreeSeatsCount;
    private int secondClassFreeSeatsCount;

    public int getRouteId() {
        return routeId;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }

    public int getTrainId() {
        return trainId;
    }

    public void setTrainId(int trainId) {
        this.trainId = trainId;
    }

    public int getRouteNumber() {
        return routeNumber;
    }

    public void setRouteNumber(int routeNumber) {
        this.routeNumber = routeNumber;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public List<Station> getStationList() {
        return stationList;
    }

    public void setStationList(List<Station> stationList) {
        this.stationList = stationList;
    }

    public int getFirstClassFreeSeatsCount() {
        return firstClassFreeSeatsCount;
    }

    public void setFirstClassFreeSeatsCount(int firstClassFreeSeatsCount) {
        this.firstClassFreeSeatsCount = firstClassFreeSeatsCount;
    }

    public int getSecondClassFreeSeatsCount() {
        return secondClassFreeSeatsCount;
    }

    public void setSecondClassFreeSeatsCount(int secondClassFreeSeatsCount) {
        this.secondClassFreeSeatsCount = secondClassFreeSeatsCount;
    }
}
