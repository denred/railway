package com.epam.redkin.model.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class RoutePoint {
    private int stationId;
    private int routeId;
    private LocalDateTime arrival;
    private LocalDateTime dispatch;
    private int orderId;

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
}
