package com.ntt.microservicetransactions.domain.model.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents data transfer object of updated bank account
 */
@Getter
@Setter
public class UpdatedBankAccountDTO {
    private String accountNumber;
    private boolean completedTransaction;
    private float amount;
    private String typeTransaction;
    private float balance;
    private int completedTransactions;

}
