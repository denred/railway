package com.epam.redkin.model.dto;

import java.util.Objects;

public class RouteInfoDTO {
    private int routsId;
    private int trainId;
    private String trainNumber;
    private String routName;
    private String routNumber;

    public RouteInfoDTO() {
    }

    public RouteInfoDTO(int routsId, int trainId, String trainNumber, String routName, String routNumber) {
        this.routsId = routsId;
        this.trainId = trainId;
        this.trainNumber = trainNumber;
        this.routName = routName;
        this.routNumber = routNumber;
    }

    public int getRoutsId() {
        return routsId;
    }

    public void setRoutsId(int routsId) {
        this.routsId = routsId;
    }

    public int getTrainId() {
        return trainId;
    }

    public void setTrainId(int trainId) {
        this.trainId = trainId;
    }

    public String getTrainNumber() {
        return trainNumber;
    }

    public void setTrainNumber(String trainNumber) {
        this.trainNumber = trainNumber;
    }

    public String getRouteName() {
        return routName;
    }

    public void setRoutName(String routName) {
        this.routName = routName;
    }

    public String getRoutNumber() {
        return routNumber;
    }

    public void setRoutNumber(String routNumber) {
        this.routNumber = routNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RouteInfoDTO)) return false;
        RouteInfoDTO that = (RouteInfoDTO) o;
        return getRoutsId() == that.getRoutsId() && getTrainId() == that.getTrainId() && getTrainNumber().equals(that.getTrainNumber()) && getRouteName().equals(that.getRouteName()) && getRoutNumber().equals(that.getRoutNumber());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRoutsId(), getTrainId(), getTrainNumber(), getRouteName(), getRoutNumber());
    }

    @Override
    public String toString() {
        return "RoutInfoDto{" +
                "routsId=" + routsId +
                ", trainId=" + trainId +
                ", trainNumber='" + trainNumber + '\'' +
                ", routName='" + routName + '\'' +
                ", routNumber='" + routNumber + '\'' +
                '}';
    }
}
