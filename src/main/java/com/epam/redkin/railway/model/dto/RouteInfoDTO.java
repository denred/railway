package com.epam.redkin.railway.model.dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RouteInfoDTO {
    private int routsId;
    private int trainId;
    private String trainNumber;
    private String routName;
    private String routNumber;
}
