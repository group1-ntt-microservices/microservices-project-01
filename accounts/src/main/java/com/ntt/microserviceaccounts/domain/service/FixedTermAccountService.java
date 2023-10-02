package com.ntt.microserviceaccounts.domain.service;

import com.ntt.microserviceaccounts.domain.model.enity.CurrentAccount;
import com.ntt.microserviceaccounts.domain.model.enity.FixedTermAccount;

import java.util.List;
import java.util.Map;

public interface FixedTermAccountService {
    List<FixedTermAccount> getAll();
    FixedTermAccount save(FixedTermAccount fixedTermAccount);
}
