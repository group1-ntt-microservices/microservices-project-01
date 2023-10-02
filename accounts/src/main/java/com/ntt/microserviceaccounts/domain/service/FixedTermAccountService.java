package com.ntt.microserviceaccounts.domain.service;


import com.ntt.microserviceaccounts.domain.model.entity.FixedTermAccount;
import java.util.List;


/**
 * Service interface for managing fixed-term accounts.
 */
public interface FixedTermAccountService {

    /**
     * Retrieves a list of all fixed-term accounts.
     *
     * @return List of fixed-term accounts.
     */
    List<FixedTermAccount> getAll();

    /**
     * Saves a new fixed-term account.
     *
     * @param fixedTermAccount The fixed-term account to be saved.
     * @return The saved fixed-term account.
     */
    FixedTermAccount save(FixedTermAccount fixedTermAccount);
}