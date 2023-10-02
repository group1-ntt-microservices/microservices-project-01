package com.ntt.microserviceaccounts.domain.service;


import com.ntt.microserviceaccounts.domain.model.entity.SavingAccount;


import java.util.List;

/**
 * Service interface for managing saving accounts.
 */
public interface SavingAccountService {

    /**
     * Retrieves a list of all saving accounts.
     *
     * @return List of saving accounts.
     */
    List<SavingAccount> getAll();

    /**
     * Saves a new saving account.
     *
     * @param account The saving account to be saved.
     * @return The saved saving account.
     */
    SavingAccount save(SavingAccount account);
}