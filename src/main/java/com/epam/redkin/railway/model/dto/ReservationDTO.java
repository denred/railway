package com.epam.redkin.railway.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReservationDTO {
    private int reservationId;
    private String status;
    private int stationId;
    private int seatId;
    private int trainId;
    private int routeId;
    private int sequenceNumber;
}
