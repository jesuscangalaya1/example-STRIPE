package com.stripe.dto;

import lombok.Data;

@Data
public class PaymentIntentDto {
    private String description;
    private int amount;
    private Currency currency;


}
