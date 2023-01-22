package com.epam.redkin.railway.model.dto;

import com.epam.redkin.railway.model.entity.CarriageType;

import java.util.Objects;

public class CarriageDTO {
    private int carId;
    private CarriageType carriageType;
    private String carNumber;
    private int trainId;
    private Integer seats;
    private double price;
    private String trainNumber;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarriageDTO carriageDTO = (CarriageDTO) o;
        return Objects.equals(carId, carriageDTO.carId) &&
                carriageType == carriageDTO.carriageType &&
                Objects.equals(carNumber, carriageDTO.carNumber) &&
                Objects.equals(trainId, carriageDTO.trainId) &&
                Objects.equals(seats, carriageDTO.seats) &&
                Objects.equals(price, carriageDTO.price) &&
                Objects.equals(trainNumber, carriageDTO.trainNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(carId, carriageType, carNumber, trainId, seats, price, trainNumber);
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public CarriageType getCarriageType() {
        return carriageType;
    }

    public void setCarriageType(CarriageType carriageType) {
        this.carriageType = carriageType;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public int getTrainId() {
        return trainId;
    }

    public void setTrainId(int trainId) {
        this.trainId = trainId;
    }

    public Integer getSeats() {
        return seats;
    }

    public void setSeats(Integer seats) {
        this.seats = seats;
    }

    public double getPrice() { return price; }

    public void setPrice(double price) { this.price = price; }

    public String getTrainNumber() {
        return trainNumber;
    }

    public void setTrainNumber(String trainNumber) {
        this.trainNumber = trainNumber;
    }
}
