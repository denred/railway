package com.epam.redkin.railway.model.entity;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Builder
public class Seat {
    private int id;
    private int carriageId;
    private String seatNumber;
    private boolean busy;
}
