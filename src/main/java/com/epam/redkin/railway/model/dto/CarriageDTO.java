package com.epam.redkin.railway.model.dto;

import com.epam.redkin.railway.model.entity.CarriageType;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class CarriageDTO {
    private int carId;
    private CarriageType carriageType;
    private String carNumber;
    private int trainId;
    private Integer seats;
    private double price;
    private String trainNumber;
}
