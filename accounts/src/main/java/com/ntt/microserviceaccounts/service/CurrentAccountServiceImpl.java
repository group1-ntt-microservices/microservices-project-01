package com.ntt.microserviceaccounts.service;

import com.ntt.microserviceaccounts.domain.model.enity.CurrentAccount;
import com.ntt.microserviceaccounts.domain.model.enity.Customer;
import com.ntt.microserviceaccounts.domain.repository.CurrentAccountRepository;

import com.ntt.microserviceaccounts.domain.service.BusinessRuleService;
import com.ntt.microserviceaccounts.domain.service.CurrentAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CurrentAccountServiceImpl implements CurrentAccountService {


    @Autowired
    private BusinessRuleService businessRuleService;

    @Autowired
    private CurrentAccountRepository currentAccountRepository;



    @Override
    public List<CurrentAccount> getAll() {
        return currentAccountRepository.findAll();
    }



    @Override
    public Map<String, Object> save(CurrentAccount currentAccount, String documentNumber) {
        Map<String, Object>  resp = new HashMap<>();

        if (!businessRuleService.validateCurrentAccount(documentNumber)){
            resp.put("succes", false);
            resp.put("message", "The business rule is not followed");
            return resp;
        }

        initializeCurrentAccount(currentAccount, documentNumber);
        currentAccountRepository.save(currentAccount);
        resp.put("succes", true);
        resp.put("message", "Checking current account registered correctly");
        return resp;
    }
    private void initializeCurrentAccount(CurrentAccount currentAccount, String documentNumber) {
        currentAccount.setId(UUID.randomUUID().toString());
        currentAccount.setDocumentNumber(documentNumber);
        currentAccount.setTypeAccount("currentAccount");
        currentAccount.setAccountHolderIds(new ArrayList<>());
        currentAccount.setAuthorizedSignatoryIds(new ArrayList<>());
    }

    @Override
    public Map<String, Object> updateCurrentAccount(String accountNumber, CurrentAccount account,String typeCustomer) {
        Map<String, Object> resp = new HashMap<>();
        CurrentAccount currentAccount = currentAccountRepository.findByAccountNumber(accountNumber);
        List<String> listDocuments = null;

        if (typeCustomer.equals("headlines")){
            listDocuments = currentAccount.getAccountHolderIds();
            listDocuments.add(account.getAccountHolderIds().get(0));
            currentAccount.setAccountHolderIds(listDocuments);
        }else if (typeCustomer.equals("authorizeds")){
            listDocuments = currentAccount.getAuthorizedSignatoryIds();
            listDocuments.add(account.getAuthorizedSignatoryIds().get(0));
            currentAccount.setAuthorizedSignatoryIds(listDocuments);
        }else{
            resp.put("succes", false);
            resp.put("message", "Internal Error");
            return resp;
        }
        resp.put("succes", true);
        resp.put("message", "Successfully registered customer");

        currentAccountRepository.save(currentAccount);

        return resp;

    }




}
