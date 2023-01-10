package com.epam.redkin.model.dto;

import com.epam.redkin.model.entity.CarriageType;
import com.epam.redkin.model.entity.OrderStatus;

import java.time.LocalDateTime;
import java.util.Objects;

public class OrderDTO {
    private String routsId;
    private String routName;
    private String routNumber;
    private String trainId;
    private String trainNumber;
    private CarriageType carType;
    private double price;
    private LocalDateTime arrivalDate;
    private LocalDateTime dispatchDate;
    private String userId;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private String arrivalStation;
    private String dispatchStation;
    private String travelTime;
    private int countOfSeats;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderDTO orderDto = (OrderDTO) o;
        return countOfSeats == orderDto.countOfSeats &&
                Objects.equals(routsId, orderDto.routsId) &&
                Objects.equals(routName, orderDto.routName) &&
                Objects.equals(routNumber, orderDto.routNumber) &&
                Objects.equals(trainId, orderDto.trainId) &&
                Objects.equals(trainNumber, orderDto.trainNumber) &&
                carType == orderDto.carType &&
                Objects.equals(price, orderDto.price) &&
                Objects.equals(arrivalDate, orderDto.arrivalDate) &&
                Objects.equals(dispatchDate, orderDto.dispatchDate) &&
                Objects.equals(userId, orderDto.userId) &&
                Objects.equals(orderDate, orderDto.orderDate) &&
                orderStatus == orderDto.orderStatus &&
                Objects.equals(arrivalStation, orderDto.arrivalStation) &&
                Objects.equals(dispatchStation, orderDto.dispatchStation) &&
                Objects.equals(travelTime, orderDto.travelTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(routsId, routName, routNumber, trainId, trainNumber, carType, price, arrivalDate, dispatchDate, userId, orderDate, orderStatus, arrivalStation, dispatchStation, travelTime, countOfSeats);
    }

    public int getCountOfSeats() {
        return countOfSeats;
    }

    public void setCountOfSeats(int countOfSeats) {
        this.countOfSeats = countOfSeats;
    }

    public String getRoutsId() {
        return routsId;
    }

    public void setRoutsId(String routsId) {
        this.routsId = routsId;
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

    public String getTrainId() {
        return trainId;
    }

    public void setTrainId(String trainId) {
        this.trainId = trainId;
    }

    public String getTrainNumber() {
        return trainNumber;
    }

    public void setTrainNumber(String trainNumber) {
        this.trainNumber = trainNumber;
    }

    public CarriageType getCarType() { return carType; }

    public void setCarType(CarriageType carType) { this.carType = carType; }

    public double getPrice() { return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public LocalDateTime getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(LocalDateTime arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public LocalDateTime getDispatchDate() {
        return dispatchDate;
    }

    public void setDispatchDate(LocalDateTime dispatchDate) {
        this.dispatchDate = dispatchDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getArrivalStation() {
        return arrivalStation;
    }

    public void setArrivalStation(String arrivalStation) {
        this.arrivalStation = arrivalStation;
    }

    public String getDispatchStation() {
        return dispatchStation;
    }

    public void setDispatchStation(String dispatchStation) {
        this.dispatchStation = dispatchStation;
    }

    public String getTravelTime() {
        return travelTime;
    }

    public void setTravelTime(String travelTime) {
        this.travelTime = travelTime;
    }
}
