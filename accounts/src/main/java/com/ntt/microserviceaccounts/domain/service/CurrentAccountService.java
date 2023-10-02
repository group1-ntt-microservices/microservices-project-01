package com.ntt.microserviceaccounts.domain.service;


import com.ntt.microserviceaccounts.domain.model.enity.CurrentAccount;
import java.util.List;




/**
 * Service interface for managing current accounts.
 */
public interface CurrentAccountService {

    /**
     * Retrieves a list of all current accounts.
     *
     * @return List of current accounts.
     */
    List<CurrentAccount> getAll();

    /**
     * Saves a new current account.
     *
     * @param account The current account to be saved.
     * @return The saved current account.
     */
    CurrentAccount save(CurrentAccount account);

    /**
     * Updates an existing current account.
     *
     * @param accountNumber The account number of the current account to be updated.
     * @param account       The updated current account data.
     * @param typeCustomer  The type of customer (headlines, authorizeds, etc.).
     * @return The updated current account.
     */
    CurrentAccount updateCurrentAccount(String accountNumber, CurrentAccount account, String typeCustomer);
}
