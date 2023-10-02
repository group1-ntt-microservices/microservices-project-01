package com.ntt.microserviceaccounts.service;


import com.ntt.microserviceaccounts.domain.model.entity.FixedTermAccount;
import com.ntt.microserviceaccounts.domain.repository.FixedTermAccountRepository;
import com.ntt.microserviceaccounts.domain.service.BusinessRuleService;
import com.ntt.microserviceaccounts.domain.service.FixedTermAccountService;
import com.ntt.microserviceaccounts.exception.CustomerNotFoundException;
import com.ntt.microserviceaccounts.exception.InternalErrorException;
import com.ntt.microserviceaccounts.exception.BusinessRulesException;
import com.ntt.microserviceaccounts.exception.ValidateAccountException;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Service class implementing operations for Fixed Term Accounts.
 */
@Service
public class FixedTermAccountServiceImpl implements FixedTermAccountService {

    @Autowired
    private BusinessRuleService businessRuleService;
    @Autowired
    private FixedTermAccountRepository fixedTermAccountRepository;
    /**
     * Retrieves all fixed-term accounts.
     *
     * @return A list of all fixed-term accounts.
     */
    @Override
    public List<FixedTermAccount> getAll() {
        return fixedTermAccountRepository.findAll();
    }

    /**
     * Saves a new fixed-term account.
     *
     * @param fixedTermAccount The fixed-term account to be saved.
     * @return The saved fixed-term account.
     */
    @Override
    public FixedTermAccount save(FixedTermAccount fixedTermAccount) {

       try {
           fixedTermAccount.setTypeAccount("fixedTermAccount");
           if (!businessRuleService.validateAccountsCustomer(fixedTermAccount.getDocumentNumber(), fixedTermAccount.getTypeAccount())){
               if (fixedTermAccount.getDocumentNumber().length() == 8){
                   throw new BusinessRulesException("A personal client cannot have more than one account ");
               }
               throw new BusinessRulesException("A business client cannot have a fixed-term account");
           }
           if (!businessRuleService.isValidAccount().test(fixedTermAccount)){
               throw new ValidateAccountException("The account number must be 14 digits and the interbank account code must be 20 digits.");
           }
           businessRuleService.validateAccountDuplicate(fixedTermAccount.getAccountNumber());
           fixedTermAccount.setId(UUID.randomUUID().toString());
           fixedTermAccount.setMaintenanceFeeFree(false);
           fixedTermAccount.setMonthlyTransactionLimit(1);
           return  fixedTermAccountRepository.save(fixedTermAccount);
       }catch (FeignException e){
           if (e.status() == HttpStatus.NOT_FOUND.value()) {
               throw new CustomerNotFoundException(fixedTermAccount.getDocumentNumber());
           }
           throw new InternalErrorException("status 500");
       }
    }



}
