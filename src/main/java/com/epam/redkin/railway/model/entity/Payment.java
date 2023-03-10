package com.epam.redkin.railway.model.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Payment {
    private String cardHolderName;
    private String cardNumber;
    private String expiryMonth;
    private String expiryYear;
    private String cvvCode;
}
