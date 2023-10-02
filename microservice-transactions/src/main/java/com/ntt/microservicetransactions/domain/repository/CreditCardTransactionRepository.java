package com.ntt.microservicetransactions.domain.repository;

import com.ntt.microservicetransactions.domain.model.entity.CreditCardTransaction;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.query.MongoEntityInformation;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Repository interface for credit card transaction.
 */
@Repository
public interface CreditCardTransactionRepository extends MongoRepository<CreditCardTransaction,String> {

    /**
     * Finds a list of CreditCardTransaction matching fields creditCardId and customerDocumentNumber
     *
     * @param creditCardId The credit card id to match.
     * @param customerDocumentNumber The customer document number to match.
     * @return List of CreditCardTransaction
     */
    @Query("{ 'creditCardId' : ?0, 'customerDocumentNumber' : ?1 }")
    List<CreditCardTransaction> findByCreditCardIdAndCustomerDocumentNumber(String creditCardId, String customerDocumentNumber);

    /**
     * Finds a list of CreditCardTransaction matching field creditCardId
     *
     * @param creditCardId The credit card id to match.
     * @return List of CreditCardTransaction
     */
    List<CreditCardTransaction> findByCreditCardId(String creditCardId);

    /**
     * Finds a list of CreditCardTransaction matching field customerDocumentNumber
     *
     * @param customerDocumentNumber The customer document number to match.
     * @return List of CreditCardTransaction
     */
    List<CreditCardTransaction> findByCustomerDocumentNumber(String customerDocumentNumber);
}
