package com.ntt.microservicetransactions.domain.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Represents data transfer object of bank account transaction
 */
@Getter
@Setter
public class BankAccountTransactionDTO {
    private String id;
    private String type;
    private Date date;
    private float amount;
    private String atm;
    private String bankAccountNumber;
    private String customerDocumentNumber;
}
