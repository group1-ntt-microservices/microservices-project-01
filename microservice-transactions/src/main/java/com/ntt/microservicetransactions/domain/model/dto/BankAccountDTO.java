package com.ntt.microservicetransactions.domain.model.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents data transfer object of bank account
 */
@Getter
@Setter
public class BankAccountDTO {
    private String id;
    private float balance;
    private String interbankAccountCode;
    private String documentNumber;
    private boolean maintenanceFeeFree;
    private String typeAccount;
    private int completedTransactions;
    private int monthlyTransactionLimit;
    private int withdrawalDay;
    private String accountNumber;
}
