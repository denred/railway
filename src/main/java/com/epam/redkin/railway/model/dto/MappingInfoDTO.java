package com.epam.redkin.railway.model.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@Builder
public class MappingInfoDTO {
    private String stationId;
    private String station;
    private int order;
    private String routsId;
    private LocalDateTime stationArrivalDate;
    private LocalDateTime stationDispatchData;
}