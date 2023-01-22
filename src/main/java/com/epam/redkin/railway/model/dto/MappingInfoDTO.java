package com.epam.redkin.railway.model.dto;

import java.time.LocalDateTime;
import java.util.Objects;

public class MappingInfoDTO {

    private String stationId;
    private String station;
    private int order;
    private String routsId;
    private LocalDateTime stationArrivalDate;
    private LocalDateTime stationDispatchData;

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
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

    public String getRoutsId() {
        return routsId;
    }

    public void setRoutsId(String routsId) {
        this.routsId = routsId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MappingInfoDTO that = (MappingInfoDTO) o;
        return order == that.order &&
                Objects.equals(stationId, that.stationId) &&
                Objects.equals(station, that.station) &&
                Objects.equals(routsId, that.routsId) &&
                Objects.equals(stationArrivalDate, that.stationArrivalDate) &&
                Objects.equals(stationDispatchData, that.stationDispatchData);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stationId, station, order, routsId, stationArrivalDate, stationDispatchData);
    }

    public void setStationDispatchData(LocalDateTime stationDispatchData) {
        this.stationDispatchData = stationDispatchData;

    }

    @Override
    public String toString() {
        return "MappingInfoDTO{" +
                "stationId='" + stationId + '\'' +
                ", station='" + station + '\'' +
                ", order=" + order +
                ", routsId='" + routsId + '\'' +
                ", stationArrivalDate=" + stationArrivalDate +
                ", stationDispatchData=" + stationDispatchData +
                '}';
    }
}