package com.epam.redkin.model.entity;

import java.util.List;
import java.util.Objects;

public class Route {
    private int routeId;
    private int trainId;
    private int routeNumber;
    private String routeName;
    private List<Station> stationList;
    private int firstClassFreeSeatsCount;
    private int secondClassFreeSeatsCount;

    public Route() {
    }

    public Route(int routeId, int trainId, int routeNumber, String routeName) {
        this.routeId = routeId;
        this.trainId = trainId;
        this.routeNumber = routeNumber;
        this.routeName = routeName;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Route)) return false;
        Route route = (Route) o;
        return getTrainId() == route.getTrainId() && getRouteNumber() == route.getRouteNumber() && getRouteName().equals(route.getRouteName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTrainId(), getRouteNumber(), getRouteName());
    }

    @Override
    public String toString() {
        return "Route{" +
                "routeId=" + routeId +
                ", trainId=" + trainId +
                ", routeNumber=" + routeNumber +
                ", routeName='" + routeName + '\'' +
                ", stationList=" + stationList +
                ", firstClassFreeSeatsCount=" + firstClassFreeSeatsCount +
                ", secondClassFreeSeatsCount=" + secondClassFreeSeatsCount +
                '}';
    }
}
