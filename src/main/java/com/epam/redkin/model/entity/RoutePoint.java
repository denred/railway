package com.epam.redkin.model.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class RoutePoint {
    private int stationId;
    private int routeId;
    private LocalDateTime arrival;
    private LocalDateTime dispatch;
    private int orderId;

    public RoutePoint() {
    }

    public RoutePoint(int stationId, int routeId, LocalDateTime arrival, LocalDateTime dispatch, int orderId) {
        this.stationId = stationId;
        this.routeId = routeId;
        this.arrival = arrival;
        this.dispatch = dispatch;
        this.orderId = orderId;
    }

    public int getStationId() {
        return stationId;
    }

    public void setStationId(int stationId) {
        this.stationId = stationId;
    }

    public int getRouteId() {
        return routeId;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }

    public LocalDateTime getArrival() {
        return arrival;
    }

    public void setArrival(LocalDateTime arrival) {
        this.arrival = arrival;
    }

    public LocalDateTime getDispatch() {
        return dispatch;
    }

    public void setDispatch(LocalDateTime dispatch) {
        this.dispatch = dispatch;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RoutePoint)) return false;
        RoutePoint that = (RoutePoint) o;
        return getStationId() == that.getStationId() && getRouteId() == that.getRouteId() && getOrderId() == that.getOrderId() && getArrival().equals(that.getArrival()) && getDispatch().equals(that.getDispatch());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStationId(), getRouteId(), getArrival(), getDispatch(), getOrderId());
    }
}
