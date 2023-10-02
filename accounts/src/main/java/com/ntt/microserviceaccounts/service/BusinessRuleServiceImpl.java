package com.ntt.microserviceaccounts.service;

import com.ntt.microserviceaccounts.domain.model.enity.BankAccount;
import com.ntt.microserviceaccounts.domain.model.dto.CustomerDTO;
import com.ntt.microserviceaccounts.domain.repository.BankAccountRepository;
import com.ntt.microserviceaccounts.domain.service.BusinessRuleService;
import com.ntt.microserviceaccounts.exception.BankAccountExistsException;
import com.ntt.microserviceaccounts.external.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@Service
public class BusinessRuleServiceImpl implements BusinessRuleService {
    @Autowired
    private CustomerService customerService;

    @Autowired
    private BankAccountRepository bankAccountRepository;
    @Override
    public boolean validateAccountsCustomer(String documentNumber,String typeAccount) {
        Optional<CustomerDTO>  customer= customerService.findByDocumentNumber(documentNumber);
        if(customer.isPresent()){
            List<BankAccount> bankAccounts = bankAccountRepository.findByDocumentNumber(documentNumber);
            if (customer.get().getCustomerType().equals("PERSONAL") ) return bankAccounts.isEmpty();
            if (customer.get().getCustomerType().equals("BUSINESS") && typeAccount.equals("currentAccount")) {

                return true;
            }
        }
        return false;
    }

    @Override
    public boolean validateAccountDuplicate(String acocuntNumber) {
        if(bankAccountRepository.existsByAccountNumber(acocuntNumber)){
            throw new BankAccountExistsException(acocuntNumber);
        }
        return false;
    }

    @Override
    public Predicate<BankAccount> isValidAccount() {
        return account -> account.getAccountNumber().length() == 14 && account.getInterbankAccountCode().length() == 20;
    }


}
