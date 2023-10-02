package com.ntt.microservicetransactions.domain.repository;

import com.ntt.microservicetransactions.domain.model.entity.CreditTransaction;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Repository interface for bank account transaction.
 */
@Repository
public interface CreditTransactionRepository extends MongoRepository<CreditTransaction,String> {

    /**
     * Finds a list of CreditTransaction matching fields creditId and customerDocumentNumber
     *
     * @param creditId The credit id to match.
     * @param customerDocumentNumber The customer document number to match.
     * @return List of CreditTransaction
     */
    @Query("{ 'creditId' : ?0, 'customerDocumentNumber' : ?1 }")
    List<CreditTransaction> findByCreditIdAndCustomerDocumentNumber(String creditId, String customerDocumentNumber);

    /**
     * Finds a list of CreditTransaction matching field customerDocumentNumber
     *
     * @param customerDocumentNumber The customer document number to match.
     * @return List of CreditTransaction
     */
    List<CreditTransaction> findByCustomerDocumentNumber(String customerDocumentNumber);

    /**
     * Finds a list of CreditTransaction matching field creditId
     *
     * @param creditId The credit id to match.
     * @return List of CreditTransaction
     */
    List<CreditTransaction> findByCreditId(String creditId);
}
