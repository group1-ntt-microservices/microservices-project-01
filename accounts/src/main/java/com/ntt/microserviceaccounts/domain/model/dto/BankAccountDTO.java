package com.ntt.microserviceaccounts.domain.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BankAccountDTO {
    private String accountNumber;
    private boolean completedTransaction;
    private float amount;
    private String typeTransaction;
    private float balance;
    private int completedTransactions;


}
