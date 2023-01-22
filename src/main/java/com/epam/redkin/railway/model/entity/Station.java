package com.epam.redkin.railway.model.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Station {
    private int id;
    private String station;
    private int orderId;
}
