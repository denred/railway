package com.epam.redkin.railway.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SeatDTO {
    private int seatId;
    private int seatNumber;
}
