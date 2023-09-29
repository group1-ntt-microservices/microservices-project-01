package com.ntt.microserviceaccounts.domain.model.enity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "savingaccounts")
public class SavingAccount extends BankAccount{
    private int monthlyTransactionLimit;
}
