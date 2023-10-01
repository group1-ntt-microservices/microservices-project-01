package com.ntt.microserviceaccounts.domain.service;


import com.ntt.microserviceaccounts.domain.model.enity.CurrentAccount;
import com.ntt.microserviceaccounts.domain.model.enity.SavingAccount;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

public interface SavingAccountService {

    List<SavingAccount> getAll();

    Map<String, Object> save(SavingAccount account, String documentNumber);
}
