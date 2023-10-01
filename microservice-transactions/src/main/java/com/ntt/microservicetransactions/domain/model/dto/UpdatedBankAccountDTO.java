package com.ntt.microservicetransactions.domain.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdatedBankAccountDTO {

    private String accountNumber;
    private boolean completedTransaction;
    private double amount;
    private String typeTransaction;
    private double balance;
    private int completedTransactions;

}
