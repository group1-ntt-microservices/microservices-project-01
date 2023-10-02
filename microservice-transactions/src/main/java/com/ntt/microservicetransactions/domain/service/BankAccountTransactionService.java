package com.ntt.microservicetransactions.domain.service;

import com.ntt.microservicetransactions.domain.model.dto.BankAccountTransactionDTO;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * A service interface for managing bank account transactions
 */
public interface BankAccountTransactionService {

    /**
     * Adds a new bank account transaction
     *
     * @param bankAccountTransactionDTO The data transfer object of bank account transaction
     * @return An instance of class BankAccountTransactionDTO.
     */
    public BankAccountTransactionDTO createBankAccountTransaction(BankAccountTransactionDTO bankAccountTransactionDTO);

    /**
     * Retrieves a list of BankAccountTransactionDTO
     *
     * @param bankAccountNumber The bank account number
     * @param customerDocumentNumber The document number of the customer
     * @return A list of class BankAccountTransactionDTO.
     */
    public List<BankAccountTransactionDTO> getFilteredBankAccountTransactions(String bankAccountNumber, String customerDocumentNumber);
}
