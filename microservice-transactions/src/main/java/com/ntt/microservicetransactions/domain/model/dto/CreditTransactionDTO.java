package com.ntt.microservicetransactions.domain.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CreditTransactionDTO {
    private String id;
    private String type;
    private Date date;
    private float amount;
    private String atm;
    private String creditId;
    private String customerDocumentNumber;
}
