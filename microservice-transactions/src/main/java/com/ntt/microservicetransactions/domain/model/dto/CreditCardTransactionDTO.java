package com.ntt.microservicetransactions.domain.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Represents data transfer object of credit card transaction
 */
@Getter
@Setter
public class CreditCardTransactionDTO {
    private String id;
    private String type;
    private Date date;
    private float amount;
    private String atm;
    private String creditCardId;
    private String customerDocumentNumber;

}


