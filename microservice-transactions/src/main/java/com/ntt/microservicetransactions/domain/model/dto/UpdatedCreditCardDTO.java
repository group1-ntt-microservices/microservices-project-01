package com.ntt.microservicetransactions.domain.model.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents data transfer object of updated credit card
 */
@Getter
@Setter

public class UpdatedCreditCardDTO {
    private String customerId;
    private String cardNumber;
    private float limitAmount;
    private float balanceAvailable;
    private float balanceDue;
}
