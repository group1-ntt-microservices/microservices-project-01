package com.ntt.microservicetransactions.domain.service;

import com.ntt.microservicetransactions.domain.model.dto.CreditCardTransactionDTO;
import com.ntt.microservicetransactions.domain.model.dto.CreditTransactionDTO;

import java.util.Date;
import java.util.List;

public interface CreditCardTransactionService {

    /**
     * Adds a new credit card transaction
     *
     * @param creditCardTransactionDTO The data transfer object of credit card transaction
     * @return An instance of class CreditCardTransactionDTO.
     */
    public CreditCardTransactionDTO createCreditCardTransaction(CreditCardTransactionDTO creditCardTransactionDTO);

    /**
     * Retrieves a list of CreditCardTransactionDTO
     *
     * @param creditCardId The ID of credit card
     * @param customerDocumentNumber The document number of the customer
     * @return A list of class CreditCardTransactionDTO.
     */
    public List<CreditCardTransactionDTO> getFilteredCreditCardTransactions(String creditCardId, String customerDocumentNumber);
}
