package com.ntt.microservicetransactions.domain.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter

public class CreditDTO {
    private String id;
    private String customerId;
    private String customerDocumentNumber;
    private String customerType;
    private float amountGranted;
    private float interest;
    private float amountInterest;
    private float amountPaid;
    private int status;
    private boolean isActive;
    private Date createdAt;
}
