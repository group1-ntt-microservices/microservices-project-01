package com.ntt.microservicetransactions.domain.service;

import com.ntt.microservicetransactions.domain.model.dto.CreditTransactionDTO;

import java.util.Date;
import java.util.List;

public interface CreditTransactionService {

    /**
     * Adds a new credit transaction
     *
     * @param creditTransactionDTO The data transfer object of credit transaction
     * @return An instance of class CreditTransactionDTO.
     */
    public CreditTransactionDTO createCreditTransaction(CreditTransactionDTO creditTransactionDTO);

    /**
     * Retrieves a list of CreditTransactionDTO
     *
     * @param creditId The ID of credit
     * @param customerDocumentNumber The document number of the customer
     * @return A list of class CreditTransactionDTO.
     */
    public List<CreditTransactionDTO> getFilteredCreditTransactions(String creditId, String customerDocumentNumber);
}
