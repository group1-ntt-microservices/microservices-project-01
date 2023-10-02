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

/**
 * Implementation of the BusinessRuleService interface. Provides methods to validate business rules related to bank accounts.
 */
@Service
public class BusinessRuleServiceImpl implements BusinessRuleService {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private BankAccountRepository bankAccountRepository;

    /**
     * Validates if a customer can have specific types of bank accounts based on their document number and account type.
     *
     * @param documentNumber The document number of the customer.
     * @param typeAccount The type of the account to be validated ("currentAccount" or others).
     * @return True if the customer can have the specified type of account, false otherwise.
     */
    @Override
    public boolean validateAccountsCustomer(String documentNumber, String typeAccount) {
        Optional<CustomerDTO> customer = customerService.findByDocumentNumber(documentNumber);
        if (customer.isPresent()) {
            List<BankAccount> bankAccounts = bankAccountRepository.findByDocumentNumber(documentNumber);
            if (customer.get().getCustomerType().equals("PERSONAL")) {
                return bankAccounts.isEmpty();
            }
            if (customer.get().getCustomerType().equals("BUSINESS") && typeAccount.equals("currentAccount")) {
                // Additional validation logic for business customers and current accounts, if needed.
                return true;
            }
        }
        return false;
    }

    /**
     * Validates if a bank account with the given account number already exists.
     *
     * @param accountNumber The account number to be validated.
     * @return True if the account number already exists, false otherwise.
     * @throws BankAccountExistsException If the account number already exists, throw an exception.
     */
    @Override
    public boolean validateAccountDuplicate(String accountNumber) {
        if (bankAccountRepository.existsByAccountNumber(accountNumber)) {
            throw new BankAccountExistsException(accountNumber);
        }
        return false;
    }

    /**
     * Provides a predicate to check if a bank account's account number and interbank account code have correct lengths.
     *
     * @return A predicate to validate bank accounts.
     */
    @Override
    public Predicate<BankAccount> isValidAccount() {
        return account -> account.getAccountNumber().length() == 14 && account.getInterbankAccountCode().length() == 20;
    }
}