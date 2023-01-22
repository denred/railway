package com.epam.redkin.railway.model.dto;

import com.epam.redkin.railway.model.entity.CarriageType;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class RoutsOrderDTO {

    private int routsId;
    private int trainId;
    private String trainNumber;
    private List<StationDTO> stations;
    private String routName;
    private int routNumber;
    private int firstClassFreeSeatsCount;
    private int secondClassFreeSeatsCount;
    private int compartmentFreeSeatsCount;
    private int berthFreeSeatsCount;
    private int luxeFreeSeatsCount;
    private HashMap<CarriageType, Integer> availableSeats;
    private HashMap<CarriageType, Double> priceInfo;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoutsOrderDTO that = (RoutsOrderDTO) o;
        return firstClassFreeSeatsCount == that.firstClassFreeSeatsCount &&
                secondClassFreeSeatsCount == that.secondClassFreeSeatsCount &&
                Objects.equals(routsId, that.routsId) &&
                Objects.equals(trainId, that.trainId) &&
                Objects.equals(trainNumber, that.trainNumber) &&
                Objects.equals(stations, that.stations) &&
                Objects.equals(routName, that.routName) &&
                Objects.equals(routNumber, that.routNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(routsId, trainId, trainNumber, stations, routName, routNumber, firstClassFreeSeatsCount, secondClassFreeSeatsCount);
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

    public String getTrainNumber() {
        return trainNumber;
    }

    public void setTrainNumber(String trainNumber) {
        this.trainNumber = trainNumber;
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

    public List<StationDTO> getStations() {
        return stations;
    }

    public void setStations(List<StationDTO> stations) {
        this.stations = stations;
    }

    public HashMap<CarriageType, Integer> getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(HashMap<CarriageType, Integer> availableSeats) {
        this.availableSeats = availableSeats;
    }

    public HashMap<CarriageType, Double> getPriceInfo() {
        return priceInfo;
    }

    public void setPriceInfo(HashMap<CarriageType, Double> priceInfo) {
        this.priceInfo = priceInfo;
    }

    @Override
    public String toString() {
        return "RoutsOrderDto{" +
                "routsId=" + routsId +
                ", trainId=" + trainId +
                ", trainNumber='" + trainNumber + '\'' +
                ", stations=" + stations +
                ", routName='" + routName + '\'' +
                ", routNumber=" + routNumber +
                ", firstClassFreeSeatsCount=" + firstClassFreeSeatsCount +
                ", secondClassFreeSeatsCount=" + secondClassFreeSeatsCount +
                '}';
    }
}
