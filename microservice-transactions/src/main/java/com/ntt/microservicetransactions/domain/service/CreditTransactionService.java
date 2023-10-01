package com.ntt.microservicetransactions.domain.service;

import com.ntt.microservicetransactions.domain.model.dto.CreditTransactionDTO;

import java.util.Date;
import java.util.List;

public interface CreditTransactionService {

    public CreditTransactionDTO createCreditTransaction(CreditTransactionDTO creditTransactionDTO);
    public List<CreditTransactionDTO> getFilteredCreditTransactions(String creditId, String customerDocumentNumber, Date startDate, Date endDate);
}
