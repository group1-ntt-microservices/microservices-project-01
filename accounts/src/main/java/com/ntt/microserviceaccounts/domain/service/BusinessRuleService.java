package com.ntt.microserviceaccounts.domain.service;

import com.ntt.microserviceaccounts.domain.model.entity.BankAccount;

import java.util.function.Predicate;

/**
 * Service interface for business rules related to bank accounts.
 */
public interface BusinessRuleService {

    /**
     * Validates if a customer can have a specific type of account.
     *
     * @param documentNumber The document number of the customer.
     * @param typeAccount    The type of account to validate.
     * @return true if the customer can have the account, false otherwise.
     */
    boolean validateAccountsCustomer(String documentNumber, String typeAccount);

    /**
     * Checks if an account number is duplicated.
     *
     * @param accountNumber The account number to check for duplicates.
     * @return true if the account number is duplicated, false otherwise.
     */
    boolean validateAccountDuplicate(String accountNumber);

    /**
     * Creates a predicate to validate account number format.
     *
     * @return Predicate to validate account number format.
     */
    Predicate<BankAccount> isValidAccount();
}