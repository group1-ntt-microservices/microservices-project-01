package com.ntt.microservicetransactions.domain.service;

import com.ntt.microservicetransactions.domain.model.dto.BankAccountTransactionDTO;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface BankAccountTransactionService {
    public BankAccountTransactionDTO createBankAccountTransaction(BankAccountTransactionDTO bankAccountTransactionDTO);
    public List<BankAccountTransactionDTO> getFilteredBankAccountTransactions(String bankAccountNumber, String customerDocumentNumber);
}
