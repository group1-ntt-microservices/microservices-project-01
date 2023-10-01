package com.ntt.microservicetransactions.domain.repository;

import com.ntt.microservicetransactions.domain.model.entity.BankAccountTransaction;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface BankAccountTransactionRepository extends MongoRepository<BankAccountTransaction,String> {

    @Query("{ 'bankAccountNumber' : ?0, 'customerDocumentNumber' : ?1 }")
    List<BankAccountTransaction> findByBankAccountNumberAndCustomerDocumentNumber(String bankAccountNumber, String customerDocumentNumber);
    List<BankAccountTransaction> findByBankAccountNumber(String bankAccountNumber);
    List<BankAccountTransaction> findByCustomerDocumentNumber(String customerDocumentNumber);
}
