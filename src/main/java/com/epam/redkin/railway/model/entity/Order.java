package com.epam.redkin.railway.model.entity;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Order {
    private String uuid;
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
}
