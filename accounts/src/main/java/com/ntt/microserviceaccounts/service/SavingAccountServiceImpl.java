package com.ntt.microserviceaccounts.service;

import com.ntt.microserviceaccounts.domain.model.enity.BankAccount;
import com.ntt.microserviceaccounts.domain.model.enity.CurrentAccount;
import com.ntt.microserviceaccounts.domain.model.enity.FixedTermAccount;
import com.ntt.microserviceaccounts.domain.model.enity.SavingAccount;
import com.ntt.microserviceaccounts.domain.repository.SavingAccountRepository;
import com.ntt.microserviceaccounts.domain.service.BankAccountService;
import com.ntt.microserviceaccounts.domain.service.BusinessRuleService;
import com.ntt.microserviceaccounts.domain.service.SavingAccountService;
import com.ntt.microserviceaccounts.exception.BusinessRulesException;
import com.ntt.microserviceaccounts.exception.CustomerNotFoundException;
import com.ntt.microserviceaccounts.exception.InternalErrorException;
import com.ntt.microserviceaccounts.exception.ValidateAccountException;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Predicate;


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
    public SavingAccount save(SavingAccount savingAccount) {
        try {
            savingAccount.setTypeAccount("savingAccount");
            if (!businessRuleService.validateAccountsCustomer(savingAccount.getDocumentNumber(), savingAccount.getTypeAccount())){
                if (savingAccount.getDocumentNumber().length() == 8){
                    throw new BusinessRulesException("A personal client cannot have more than one account");
                }
                throw new BusinessRulesException("A business client cannot have a Saving Account account");
            }
            if (!businessRuleService.isValidAccount().test(savingAccount)){
                throw new ValidateAccountException("The account number must be 14 digits and the interbank account code must be 20 digits.");
            }
            businessRuleService.validateAccountDuplicate(savingAccount.getAccountNumber());
            savingAccount.setId(UUID.randomUUID().toString());
            savingAccount.setMaintenanceFeeFree(false);
            return savingAccountRepository.save(savingAccount);
        }catch (FeignException e){
            if (e.status() == HttpStatus.NOT_FOUND.value()) {
                throw new CustomerNotFoundException(savingAccount.getDocumentNumber());
            }
            throw new InternalErrorException("status 500");

        }

    }

}
