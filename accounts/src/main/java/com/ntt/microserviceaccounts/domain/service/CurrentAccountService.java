package com.ntt.microserviceaccounts.domain.service;

import com.ntt.microserviceaccounts.domain.model.enity.BankAccount;
import com.ntt.microserviceaccounts.domain.model.enity.CurrentAccount;
import java.util.List;
import java.util.Map;


public interface CurrentAccountService {
    List<CurrentAccount> getAll();
    CurrentAccount save(CurrentAccount account);
    CurrentAccount updateCurrentAccount(String accountNumber, CurrentAccount account,String typeCustomer);
}
