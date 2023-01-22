package com.epam.redkin.railway.model.builder;

import com.epam.redkin.railway.model.entity.CarriageType;
import com.epam.redkin.railway.model.entity.Order;
import com.epam.redkin.railway.model.entity.OrderStatus;
import com.epam.redkin.railway.model.entity.User;

import java.time.LocalDateTime;

public class OrderBuilder {
    private int id;
    private LocalDateTime orderDate;
    private int routeId;
    private String routeName;
    private String dispatchStation;
    private LocalDateTime dispatchDate;
    private String arrivalStation;
    private LocalDateTime arrivalDate;
    private String travelTime;
    private String trainNumber;
    private String carriageNumber;
    private CarriageType carriageType;
    private int countOfSeats;
    private String seatNumber;
    private String seatsId;
    private User user;
    private double price;
    private OrderStatus orderStatus;

    public int getId() {
        return id;
    }

    public OrderBuilder setId(int id) {
        this.id = id;
        return this;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public OrderBuilder setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
        return this;
    }

    public int getRouteId() {
        return routeId;
    }

    public OrderBuilder setRouteId(int routeId) {
        this.routeId = routeId;
        return this;
    }

    public String getRouteName() {
        return routeName;
    }

    public OrderBuilder setRouteName(String routeName) {
        this.routeName = routeName;
        return this;
    }

    public String getDispatchStation() {
        return dispatchStation;
    }

    public OrderBuilder setDispatchStation(String dispatchStation) {
        this.dispatchStation = dispatchStation;
        return this;
    }

    public LocalDateTime getDispatchDate() {
        return dispatchDate;
    }

    public OrderBuilder setDispatchDate(LocalDateTime dispatchDate) {
        this.dispatchDate = dispatchDate;
        return this;
    }

    public String getArrivalStation() {
        return arrivalStation;
    }

    public OrderBuilder setArrivalStation(String arrivalStation) {
        this.arrivalStation = arrivalStation;
        return this;
    }

    public LocalDateTime getArrivalDate() {
        return arrivalDate;
    }

    public OrderBuilder setArrivalDate(LocalDateTime arrivalDate) {
        this.arrivalDate = arrivalDate;
        return this;
    }

    public String getTravelTime() {
        return travelTime;
    }

    public OrderBuilder setTravelTime(String travelTime) {
        this.travelTime = travelTime;
        return this;
    }

    public String getTrainNumber() {
        return trainNumber;
    }

    public OrderBuilder setTrainNumber(String trainNumber) {
        this.trainNumber = trainNumber;
        return this;
    }

    public String getCarriageNumber() {
        return carriageNumber;
    }

    public OrderBuilder setCarriageNumber(String carriageNumber) {
        this.carriageNumber = carriageNumber;
        return this;
    }

    public CarriageType getCarriageType() {
        return carriageType;
    }

    public OrderBuilder setCarriageType(String carriageType) {
        this.carriageType = CarriageType.valueOf(carriageType);
        return this;
    }

    public int getCountOfSeats() {
        return countOfSeats;
    }

    public OrderBuilder setCountOfSeats(int countOfSeats) {
        this.countOfSeats = countOfSeats;
        return this;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public OrderBuilder setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
        return this;
    }

    public String getSeatsId() {
        return seatsId;
    }

    public OrderBuilder setSeatsId(String seatsId) {
        this.seatsId = seatsId;
        return this;
    }

    public User getUser() {
        return user;
    }

    public OrderBuilder setUser(User user) {
        this.user = user;
        return this;
    }

    public double getPrice() {
        return price;
    }

    public OrderBuilder setPrice(double price) {
        this.price = price;
        return this;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public OrderBuilder setOrderStatus(String orderStatus) {
        this.orderStatus = OrderStatus.valueOf(orderStatus);
        return this;
    }

    public Order build(){
        return new Order(this);
    }
}
