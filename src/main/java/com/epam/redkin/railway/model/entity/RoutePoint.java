package com.epam.redkin.railway.model.entity;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class RoutePoint {
    private int stationId;
    private int routeId;
    private LocalDateTime arrival;
    private LocalDateTime dispatch;
    private int orderId;
}
