package com.ntt.microservicetransactions.domain.service;

import com.ntt.microservicetransactions.domain.model.dto.CreditCardTransactionDTO;
import com.ntt.microservicetransactions.domain.model.dto.CreditTransactionDTO;

import java.util.Date;
import java.util.List;

public interface CreditCardTransactionService {

    public CreditCardTransactionDTO createCreditCardTransaction(CreditCardTransactionDTO creditCardTransactionDTO);
    public List<CreditCardTransactionDTO> getFilteredCreditCardTransactions(String creditCardId, String customerDocumentNumber);
}
