package com.epam.redkin.model.entity;

public class Station {
    private int id;
    private String station;
    private int orderId;

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
    public String toString() {
        return "Station{" +
                "id=" + id +
                ", station='" + station + '\'' +
                '}';
    }
}
