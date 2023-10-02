package com.ntt.microserviceaccounts.service;

import com.ntt.microserviceaccounts.domain.model.dto.BusinessCustomerDTO;
import com.ntt.microserviceaccounts.domain.model.dto.CustomerDTO;
import com.ntt.microserviceaccounts.domain.model.entity.CurrentAccount;
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

/**
 * Implementation of the CurrentAccountService interface. Provides methods for managing current accounts.
 */
@Service
public class CurrentAccountServiceImpl implements CurrentAccountService {


    @Autowired
    private BusinessRuleService businessRuleService;

    @Autowired
    private CurrentAccountRepository currentAccountRepository;

    @Autowired
    private CustomerService customerService;




    /**
     * Retrieves all current accounts.
     *
     * @return List of current accounts.
     */
    @Override
    public List<CurrentAccount> getAll() {
        return currentAccountRepository.findAll();
    }

    /**
     * Saves a new current account after performing necessary validations.
     *
     * @param currentAccount The current account object to be saved.
     * @return The saved current account.
     * @throws BusinessRulesException     If business rules are violated.
     * @throws ValidateAccountException    If account validation fails.
     * @throws DuplicateDocumetNumberException If a duplicate document number is encountered.
     */
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

            if (currentAccount.getDocumentNumber().length() == 8){
                Optional<CustomerDTO> customerDTO = customerService.findByDocumentNumber(currentAccount.getDocumentNumber());
                initializeCurrentAccountPersonal(currentAccount);
                return currentAccountRepository.save(currentAccount);
            }

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

    /**
     * Updates a current account based on the provided account number and customer type.
     *
     * @param accountNumber The account number of the current account to be updated.
     * @param account       The updated current account object.
     * @param typeCustomer  The type of the customer ("headlines" or "authorizeds").
     * @return The updated current account.
     * @throws CustomerNotFoundException      If the customer is not found.
     * @throws DuplicateDocumetNumberException If a duplicate document number is encountered.
     */
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
    /**
     * Validates if a document number is duplicated within the account holders or authorized signatories list.
     *
     * @param accountHolderIds       The list of account holders.
     * @param authorizedSignatoryIds The list of authorized signatories.
     * @param newDocumentNumber      The new document number to be validated.
     * @throws DuplicateDocumetNumberException If a duplicate document number is encountered.
     */
    public void validateDuplicatedDocumentNumber(List<String> accountHolderIds, List<String> authorizedSignatoryIds, String newDocumentNumber){
        if (accountHolderIds.stream().anyMatch(id -> id.equals(newDocumentNumber)) ||
                authorizedSignatoryIds.stream().anyMatch(id -> id.equals(newDocumentNumber))) {
            throw new DuplicateDocumetNumberException(newDocumentNumber);
        }
    }
    /**
     * Initializes the account holders list of a current account based on business customer information.
     *
     * @param currentAccount        The current account to be initialized.
     * @param businessCustomerDTO   The business customer DTO, if available.
     */
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

    /**
     * Initializes the account holders list of a personal current account.
     *
     * @param currentAccount The current account to be initialized.
     */
    private void initializeCurrentAccountPersonal(CurrentAccount currentAccount) {
        currentAccount.setId(UUID.randomUUID().toString());
        currentAccount.setMaintenanceFeeFree(true);
        currentAccount.setAccountHolderIds(new ArrayList<>());
        currentAccount.setAuthorizedSignatoryIds(new ArrayList<>());
    }


}
