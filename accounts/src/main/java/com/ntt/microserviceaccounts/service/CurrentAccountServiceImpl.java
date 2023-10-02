package com.ntt.microserviceaccounts.service;

import com.ntt.microserviceaccounts.domain.model.dto.BusinessCustomerDTO;
import com.ntt.microserviceaccounts.domain.model.dto.CustomerDTO;
import com.ntt.microserviceaccounts.domain.model.enity.CurrentAccount;
import com.ntt.microserviceaccounts.domain.repository.CurrentAccountRepository;

import com.ntt.microserviceaccounts.domain.service.BusinessRuleService;
import com.ntt.microserviceaccounts.domain.service.CurrentAccountService;
import com.ntt.microserviceaccounts.exception.*;
import com.ntt.microserviceaccounts.external.CustomerService;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Predicate;

@Service
public class CurrentAccountServiceImpl implements CurrentAccountService {


    @Autowired
    private BusinessRuleService businessRuleService;

    @Autowired
    private CurrentAccountRepository currentAccountRepository;

    @Autowired
    private CustomerService customerService;





    @Override
    public List<CurrentAccount> getAll() {
        return currentAccountRepository.findAll();
    }

    @Override
    public CurrentAccount save(CurrentAccount currentAccount) {

        try {
            currentAccount.setTypeAccount("currentAccount");
            if (!businessRuleService.validateAccountsCustomer(currentAccount.getDocumentNumber(), currentAccount.getTypeAccount())){
                if (currentAccount.getDocumentNumber().length() == 8){
                    throw new BusinessRulesException("A personal client cannot have more than one account");
                }
            }

            if (!businessRuleService.isValidAccount().test(currentAccount)){
                throw new ValidateAccountException("The account number must be 14 digits and the interbank account code must be 20 digits.");
            }
            businessRuleService.validateAccountDuplicate(currentAccount.getAccountNumber());

            Optional<BusinessCustomerDTO> businessCustomerDTO = customerService.findByBusinessDocumentNumber(currentAccount.getDocumentNumber());
            initializeCurrentAccount(currentAccount, businessCustomerDTO);
            return currentAccountRepository.save(currentAccount);
        }catch (FeignException e){
            if (e.status() == HttpStatus.NOT_FOUND.value()) {
                throw new CustomerNotFoundException(currentAccount.getDocumentNumber());
            }
            throw new InternalErrorException("status 500");
        }

    }


    @Override
    public CurrentAccount updateCurrentAccount(String accountNumber, CurrentAccount account,String typeCustomer) {

        try {
            List<String> listAccountHolderIds;
            List<String> listAuthorizedSignatoryIds;

            CurrentAccount currentAccount = currentAccountRepository.findByAccountNumber(accountNumber);
            Optional<CustomerDTO> customerDTO;
            if (currentAccount == null){
                throw new BankAccountNotFoundException(accountNumber);
            }
            listAccountHolderIds = currentAccount.getAccountHolderIds();
            listAuthorizedSignatoryIds = currentAccount.getAuthorizedSignatoryIds();

            String documentNumber;
            if (typeCustomer.equals("headlines")){
                documentNumber = account.getAccountHolderIds().get(0);;
                customerDTO = customerService.findByDocumentNumber(documentNumber);
                validateDuplicatedDocumentNumber(listAccountHolderIds, listAuthorizedSignatoryIds, documentNumber);
                listAccountHolderIds.add(account.getAccountHolderIds().get(0));
                currentAccount.setAccountHolderIds(listAccountHolderIds);
            }else if (typeCustomer.equals("authorizeds")){
                documentNumber = account.getAuthorizedSignatoryIds().get(0);
                customerDTO = customerService.findByDocumentNumber(documentNumber);
                validateDuplicatedDocumentNumber(listAccountHolderIds, listAuthorizedSignatoryIds, documentNumber);
                listAuthorizedSignatoryIds.add(documentNumber);
                currentAccount.setAuthorizedSignatoryIds(listAuthorizedSignatoryIds);
            }

            return  currentAccountRepository.save(currentAccount);

        }catch (FeignException e){
            if (e.status() == HttpStatus.NOT_FOUND.value()) {
                throw new CustomerNotFoundException(account.getAccountHolderIds().get(0));
            }
            throw new CustomerNotFoundException(account.getAccountHolderIds().get(0));
        }


    }

    public Predicate<CurrentAccount> isValidAccount() {
        return account -> account.getAccountNumber().length() == 14 && account.getInterbankAccountCode().length() == 20;
    }
    public void validateDuplicatedDocumentNumber(List<String> accountHolderIds, List<String> authorizedSignatoryIds, String newDocumentNumber){
        if (accountHolderIds.stream().anyMatch(id -> id.equals(newDocumentNumber)) ||
                authorizedSignatoryIds.stream().anyMatch(id -> id.equals(newDocumentNumber))) {
            throw new DuplicateDocumetNumberException(newDocumentNumber);
        }
    }
    private void initializeCurrentAccount(CurrentAccount currentAccount, Optional<BusinessCustomerDTO> businessCustomerDTO) {
        List<String> listDocuments = new ArrayList<>();
        businessCustomerDTO.ifPresent(dto -> {
            String representativeDocumentNumber = dto.getRepresentativeDocumentNumber();
            listDocuments.add(representativeDocumentNumber);
            currentAccount.setAccountHolderIds(listDocuments);
        });
        currentAccount.setId(UUID.randomUUID().toString());
        currentAccount.setMaintenanceFeeFree(true);
        currentAccount.setAuthorizedSignatoryIds(new ArrayList<>());
    }




}
