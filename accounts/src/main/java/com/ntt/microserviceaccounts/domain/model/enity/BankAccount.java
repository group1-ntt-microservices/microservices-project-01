package com.ntt.microserviceaccounts.domain.model.enity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "bankaccounts")
@Inheritance(strategy = InheritanceType.JOINED)
public class BankAccount {

    @Id
    private String id;
    private double balance;
    private String accountNumber;
    private String interbankAccountCode;
    private String documentNumber;
    private boolean maintenanceFeeFree;
    private String typeAccount;
    private int completedTransactions;

}
