package com.ntt.microserviceaccounts.domain.service;

import com.ntt.microserviceaccounts.domain.model.enity.BankAccount;
import com.ntt.microserviceaccounts.domain.model.enity.CurrentAccount;
import java.util.List;
import java.util.Map;


public interface CurrentAccountService {
    List<CurrentAccount> getAll();
    Map<String, Object> save(CurrentAccount account, String documentNumber);
    Map<String, Object> updateCurrentAccount(String accountNumber, CurrentAccount account,String typeCustomer);
}
