package com.epam.redkin.railway.model.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Seat {
    private int id;
    private int carriageId;
    private String seatNumber;
    private boolean busy;
}
