package com.ntt.microserviceaccounts.domain.service;

import com.ntt.microserviceaccounts.domain.model.enity.BankAccount;
import com.ntt.microserviceaccounts.domain.model.enity.CurrentAccount;
import com.ntt.microserviceaccounts.domain.model.enity.Customer;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.UUID;

public interface BankAccountService {


    List<BankAccount> getAll();

    BankAccount save(BankAccount bankAccount);
    List<BankAccount> getAllAccountsCustomer(String documentNumber);

    BankAccount getBankAccount(String accountNumber);
    boolean validateAccount(String accountNumber);

    BankAccount updateBankAccount(String accountNumber, BankAccount bankAccount);
}
