package com.ntt.microserviceaccounts.domain.service;

import com.ntt.microserviceaccounts.domain.model.dto.BankAccountDTO;
import com.ntt.microserviceaccounts.domain.model.enity.BankAccount;

import java.util.List;
import java.util.Optional;

public interface BankAccountService {


    List<BankAccount> getAll();

    BankAccount save(BankAccount bankAccount);
    List<BankAccount> getAllAccountsCustomer(String documentNumber);

    Optional<BankAccount> getBankAccount(String accountNumber);
    boolean validateAccount(String accountNumber);

    BankAccount updateBankAccount(String accountNumber, BankAccountDTO bankAccountDTO);

    void deleteAccount(String id);
}
