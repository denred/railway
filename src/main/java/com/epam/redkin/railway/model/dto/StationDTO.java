package com.epam.redkin.railway.model.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Builder
public class StationDTO {
    private int stationId;
    private String station;
    private int order;
    private LocalDateTime stationArrivalDateTime;
    private LocalDate stationArrivalDate;
    private LocalTime stationArrivalTime;
    private LocalDateTime stationDispatchDateTime;
    private LocalDate stationDispatchDate;
    private LocalTime stationDispatchTime;
    private String routName;
    private int routNumber;
    private int routsId;
    private int trainId;
    private String trainNumber;
}
