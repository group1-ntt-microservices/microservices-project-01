package com.ntt.microserviceaccounts.service;

import com.ntt.microserviceaccounts.domain.model.enity.CurrentAccount;
import com.ntt.microserviceaccounts.domain.model.enity.SavingAccount;
import com.ntt.microserviceaccounts.domain.repository.SavingAccountRepository;
import com.ntt.microserviceaccounts.domain.service.BankAccountService;
import com.ntt.microserviceaccounts.domain.service.BusinessRuleService;
import com.ntt.microserviceaccounts.domain.service.SavingAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@Service
public class SavingAccountServiceImpl implements SavingAccountService {

    @Autowired
    private BusinessRuleService businessRuleService;
    @Autowired
    private SavingAccountRepository savingAccountRepository;

    @Override
    public List<SavingAccount> getAll() {
        return savingAccountRepository.findAll();
    }


    @Override
    public Map<String, Object> save(SavingAccount savingAccount, String documentNumber) {
        Map<String, Object>  resp = new HashMap<>();
        try {
            if (!businessRuleService.validateSavingsAndFixedAccount(documentNumber)){
                resp.put("success", 0);
                resp.put("message", "The business rule is not followed");
                return resp;
            }
            savingAccount.setId(UUID.randomUUID().toString());
            savingAccount.setDocumentNumber(documentNumber);
            savingAccount.setTypeAccount("savingAccount");
            savingAccountRepository.save(savingAccount);
            resp.put("success", 1);
            resp.put("message", "Checking Saving Account account registered correctly");
        }catch (Exception e){
            resp.put("success", -1);
            resp.put("message", "Internal Error :"+e.getMessage());

        }
        return resp;
    }

}
