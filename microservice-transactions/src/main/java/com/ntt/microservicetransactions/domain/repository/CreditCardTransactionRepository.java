package com.ntt.microservicetransactions.domain.repository;

import com.ntt.microservicetransactions.domain.model.entity.CreditCardTransaction;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CreditCardTransactionRepository extends MongoRepository<CreditCardTransaction,String> {

    @Query("{ 'creditCardId' : ?0, 'customerDocumentNumber' : ?1 }")
    List<CreditCardTransaction> findByCreditCardIdAndCustomerDocumentNumber(String creditCardId, String customerDocumentNumber);
    List<CreditCardTransaction> findByCreditCardId(String creditCardId);
    List<CreditCardTransaction> findByCustomerDocumentNumber(String customerDocumentNumber);
}
