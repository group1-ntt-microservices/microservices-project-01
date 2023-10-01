package com.ntt.microservicetransactions.domain.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

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
