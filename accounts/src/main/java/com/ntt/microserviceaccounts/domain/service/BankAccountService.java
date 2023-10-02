package com.ntt.microserviceaccounts.domain.service;

import com.ntt.microserviceaccounts.domain.model.dto.BankAccountDTO;
import com.ntt.microserviceaccounts.domain.model.entity.BankAccount;

import java.util.List;
import java.util.Map;
import java.util.Optional;


/**
 * Service interface for managing bank accounts.
 */
public interface BankAccountService {

    /**
     * Retrieves all bank accounts.
     *
     * @return List of bank accounts.
     */
    List<BankAccount> getAll();

    /**
     * Saves a bank account.
     *
     * @param bankAccount The bank account to be saved.
     * @return The saved bank account.
     */
    BankAccount save(BankAccount bankAccount);

    /**
     * Retrieves all bank accounts associated with a specific customer.
     *
     * @param documentNumber The document number of the customer.
     * @return List of bank accounts associated with the customer.
     */
    List<BankAccount> getAllAccountsCustomer(String documentNumber);

    /**
     * Retrieves a bank account by its account number.
     *
     * @param accountNumber The account number of the bank account.
     * @return Optional containing the bank account with the given account number, if it exists.
     */
    Optional<BankAccount> getBankAccount(String accountNumber);

    /**
     * Updates a bank account's balance and transaction information.
     *
     * @param accountNumber  The account number of the bank account to update.
     * @param bankAccountDTO The data to update the bank account.
     * @return The updated bank account.
     */
    BankAccount updateBankAccount(String accountNumber, BankAccountDTO bankAccountDTO);

    /**
     * Deletes a bank account by its ID.
     *
     * @param id The ID of the bank account to be deleted.
     */
    void deleteAccount(String id);
    /**
     * This method retrieves specific data from bank accounts, such as balance, account number,
     * document number, and the number of transactions for a given customer identified by document number.
     *
     * @param documentNumber The document number of the customer to retrieve bank account data for.
     * @return A list of maps, where each map represents a bank account's selected fields.
     */
    List<Map<String, Object>> getBalancesAccounts(String documentNumber);
}