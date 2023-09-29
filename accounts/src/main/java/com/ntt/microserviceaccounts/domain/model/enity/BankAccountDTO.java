package com.ntt.microserviceaccounts.domain.model.enity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BankAccountDTO {
    private String accountNumber;
    private boolean completedTransaction;
    private double amount;
    private String typeTransaction;
    private double balance;
    private int completedTransactions;


}
