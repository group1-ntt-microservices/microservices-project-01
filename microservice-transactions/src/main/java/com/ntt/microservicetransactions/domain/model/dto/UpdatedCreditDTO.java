package com.ntt.microservicetransactions.domain.model.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents data transfer object of updated credit
 */
@Getter
@Setter

public class UpdatedCreditDTO {
    private String customerId;
    private float amountGranted;
    private float interest;
    private float amountInterest;
    private float amountPaid;
}
