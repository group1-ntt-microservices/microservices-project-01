package com.ntt.microserviceaccounts.service;

import com.ntt.microserviceaccounts.domain.model.enity.BankAccount;
import com.ntt.microserviceaccounts.domain.model.enity.Customer;
import com.ntt.microserviceaccounts.domain.repository.BankAccountRepository;
import com.ntt.microserviceaccounts.domain.service.BusinessRuleService;
import com.ntt.microserviceaccounts.external.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BusinessRuleServiceImpl implements BusinessRuleService {
    @Autowired
    private CustomerService customerService;

    @Autowired
    private BankAccountRepository bankAccountRepository;
    @Override
    public boolean validateSavingsAndFixedAccount(String documentNumber) {
        Optional<Customer>  customer= customerService.findByDocumentNumber(documentNumber);
        if(customer.isPresent()){
            List<BankAccount> bankAccounts = bankAccountRepository.findByDocumentNumber(documentNumber);
            if (customer.get().getCustomerType().equals("PERSONAL")) return bankAccounts.isEmpty();
        }
        return false;
    }
    @Override
    public boolean validateCurrentAccount(String documentNumber) {
        Optional<Customer>  customer= customerService.findByDocumentNumber(documentNumber);
        if(customer.isPresent()){
            List<BankAccount> bankAccounts = bankAccountRepository.findByDocumentNumber(documentNumber);
            if (customer.get().getCustomerType().equals("PERSONAL")) return bankAccounts.isEmpty();
            return customer.get().getCustomerType().equals("BUSINESS");
        }
        return false;
    }

    public boolean accountHholderOrAuthorized(String documentNumber){

        return true;
    }

}
