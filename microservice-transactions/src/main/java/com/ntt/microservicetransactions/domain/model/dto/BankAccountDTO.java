package com.ntt.microservicetransactions.domain.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BankAccountDTO {
    private Long id;
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
