package com.epam.redkin.model.dto;

import java.time.LocalDateTime;
import java.util.Objects;

public class StationDTO {

    private int stationId;
    private String station;
    private int order;
    private LocalDateTime stationArrivalDate;
    private LocalDateTime stationDispatchData;
    private String routName;
    private int routNumber;
    private int routsId;
    private int trainId;
    private String trainNumber;

    public StationDTO() {
    }

    public StationDTO(int stationId, String station, int order, LocalDateTime stationArrivalDate, LocalDateTime stationDispatchData, String routName, int routNumber, int routsId, int trainId, String trainNumber) {
        this.stationId = stationId;
        this.station = station;
        this.order = order;
        this.stationArrivalDate = stationArrivalDate;
        this.stationDispatchData = stationDispatchData;
        this.routName = routName;
        this.routNumber = routNumber;
        this.routsId = routsId;
        this.trainId = trainId;
        this.trainNumber = trainNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StationDTO that = (StationDTO) o;
        return order == that.order &&
                Objects.equals(stationId, that.stationId) &&
                Objects.equals(station, that.station) &&
                Objects.equals(stationArrivalDate, that.stationArrivalDate) &&
                Objects.equals(stationDispatchData, that.stationDispatchData) &&
                Objects.equals(routName, that.routName) &&
                Objects.equals(routNumber, that.routNumber) &&
                Objects.equals(routsId, that.routsId) &&
                Objects.equals(trainId, that.trainId) &&
                Objects.equals(trainNumber, that.trainNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stationId, station, order, stationArrivalDate, stationDispatchData, routName, routNumber, routsId, trainId, trainNumber);
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

    public int getStationId() {
        return stationId;
    }

    public void setStationId(int stationId) {
        this.stationId = stationId;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public LocalDateTime getStationArrivalDate() {
        return stationArrivalDate;
    }

    public void setStationArrivalDate(LocalDateTime stationArrivalDate) {
        this.stationArrivalDate = stationArrivalDate;
    }

    public LocalDateTime getStationDispatchData() {
        return stationDispatchData;
    }

    public void setStationDispatchData(LocalDateTime stationDispatchData) {
        this.stationDispatchData = stationDispatchData;
    }

    public String getRoutName() {
        return routName;
    }

    public void setRoutName(String routName) {
        this.routName = routName;
    }

    public int getRoutNumber() {
        return routNumber;
    }

    public void setRoutNumber(int routNumber) {
        this.routNumber = routNumber;
    }

    public int getRoutsId() {
        return routsId;
    }

    public void setRoutsId(int routsId) {
        this.routsId = routsId;
    }

    @Override
    public String
    toString() {
        return "StationDto{" +
                "stationId=" + stationId +
                ", station='" + station + '\'' +
                ", order=" + order +
                ", stationArrivalDate=" + stationArrivalDate +
                ", stationDispatchData=" + stationDispatchData +
                ", routName='" + routName + '\'' +
                ", routNumber=" + routNumber +
                ", routsId=" + routsId +
                ", trainId=" + trainId +
                ", trainNumber='" + trainNumber + '\'' +
                '}';
    }
}
