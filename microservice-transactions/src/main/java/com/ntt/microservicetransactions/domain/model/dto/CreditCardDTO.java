package com.ntt.microservicetransactions.domain.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CreditCardDTO {
    private String id;
    private String customerId;
    private String customerDocumentNumber;
    private String customerType;
    private String cardNumber;
    private float limitAmount;
    private float balanceAvailable;
    private float balanceDue;
    private boolean isActive;
    private Date createdAt;

}