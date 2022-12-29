package com.epam.redkin.model.dto;

import java.util.Objects;

public class RoutInfoDto {
    private int routsId;
    private int trainId;
    private String trainNumber;
    private String routName;
    private String routNumber;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoutInfoDto that = (RoutInfoDto) o;
        return Objects.equals(routsId, that.routsId) &&
                Objects.equals(trainId, that.trainId) &&
                Objects.equals(trainNumber, that.trainNumber) &&
                Objects.equals(routName, that.routName) &&
                Objects.equals(routNumber, that.routNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(routsId, trainId, trainNumber, routName, routNumber);
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

    public String getRoutName() {
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
