package com.ntt.microservicetransactions.domain.repository;

import com.ntt.microservicetransactions.domain.model.entity.BankAccountTransaction;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for bank account transaction.
 */
public interface BankAccountTransactionRepository extends MongoRepository<BankAccountTransaction,String> {

    /**
     * Finds a list of BankAccountTransaction matching fields bankAccountNumber and customerDocumentNumber
     *
     * @param bankAccountNumber The bank account number to match.
     * @param customerDocumentNumber The customer document number to match.
     * @return List of BankAccountTransaction
     */
    @Query("{ 'bankAccountNumber' : ?0, 'customerDocumentNumber' : ?1 }")
    List<BankAccountTransaction> findByBankAccountNumberAndCustomerDocumentNumber(String bankAccountNumber, String customerDocumentNumber);

    /**
     * Finds a list of BankAccountTransaction matching fields bankAccountNumber
     *
     * @param bankAccountNumber The bank account number to match.
     * @return List of BankAccountTransaction
     */
    List<BankAccountTransaction> findByBankAccountNumber(String bankAccountNumber);

    /**
     * Finds a list of BankAccountTransaction matching fields customerDocumentNumber
     *
     * @param customerDocumentNumber The customer document number to match.
     * @return List of BankAccountTransaction
     */
    List<BankAccountTransaction> findByCustomerDocumentNumber(String customerDocumentNumber);
}
