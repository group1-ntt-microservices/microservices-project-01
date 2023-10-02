package com.ntt.microserviceaccounts.service;


import com.ntt.microserviceaccounts.domain.model.enity.FixedTermAccount;
import com.ntt.microserviceaccounts.domain.repository.FixedTermAccountRepository;
import com.ntt.microserviceaccounts.domain.service.BankAccountService;
import com.ntt.microserviceaccounts.domain.service.BusinessRuleService;
import com.ntt.microserviceaccounts.domain.service.FixedTermAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class FixedTermAccountServiceImpl implements FixedTermAccountService {

    @Autowired
    private BusinessRuleService businessRuleService;
    @Autowired
    private FixedTermAccountRepository fixedTermAccountRepository;

    @Override
    public List<FixedTermAccount> getAll() {
        return fixedTermAccountRepository.findAll();
    }

    @Override
    public Map<String, Object> save(FixedTermAccount fixedTermAccount, String documentNumber) {
        Map<String, Object>  resp = new HashMap<>();
        if (!businessRuleService.validateSavingsAndFixedAccount(documentNumber)){
           resp.put("succes", false);
           resp.put("message", "The business rule is not followed");
           return resp;
        }
        fixedTermAccount.setId(UUID.randomUUID().toString());
        fixedTermAccount.setDocumentNumber(documentNumber);
        fixedTermAccount.setTypeAccount("fixedAccount");
        fixedTermAccountRepository.save(fixedTermAccount);
        resp.put("succes", true);
        resp.put("message", "Checking Fixed Term Account account registered correctly");
        return resp;
    }

}
