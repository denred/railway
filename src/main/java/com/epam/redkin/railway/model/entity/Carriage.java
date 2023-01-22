package com.epam.redkin.railway.model.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Carriage {
    private int carriageId;
    private CarriageType type;
    private String number;
    private int trainId;
}
