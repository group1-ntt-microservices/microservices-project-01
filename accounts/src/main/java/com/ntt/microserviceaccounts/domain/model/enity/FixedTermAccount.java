package com.ntt.microserviceaccounts.domain.model.enity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Entity representing a fixed-term account, inheriting properties from BankAccount.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "fixedtermaccounts")
public class FixedTermAccount extends BankAccount{
        private int withdrawalDay;
        private int monthlyTransactionLimit;
}
