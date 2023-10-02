package com.ntt.microserviceaccounts.domain.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/**
 * Data Transfer Object (DTO) representing a bank account.
 * Contains essential account information for transactions and balance management.
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BankAccountDTO {
    private String accountNumber;
    private boolean completedTransaction;
    private float amount;
    private String typeTransaction;
    private float balance;
    private int completedTransactions;

}
