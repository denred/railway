package com.epam.redkin.model.entity;

import java.util.Objects;

public class Station {
    private int id;
    private String station;
    private int orderId;

    public Station() {
    }

    public Station(String station) {
        this.station = station;
    }

    public Station(int id, String station) {
        this.id = id;
        this.station = station;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
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
        if (!(o instanceof Station)) return false;
        Station station1 = (Station) o;
        return Objects.equals(getStation(), station1.getStation());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStation());
    }

    @Override
    public String toString() {
        return "Station{" +
                "id=" + id +
                ", station='" + station + '\'' +
                '}';
    }
}
