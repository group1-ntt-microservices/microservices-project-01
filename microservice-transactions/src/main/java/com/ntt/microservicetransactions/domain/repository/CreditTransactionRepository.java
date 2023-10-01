package com.ntt.microservicetransactions.domain.repository;

import com.ntt.microservicetransactions.domain.model.entity.CreditTransaction;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CreditTransactionRepository extends MongoRepository<CreditTransaction,String> {

    @Query("{ 'creditId' : ?0, 'customerDocumentNumber' : ?1 }")
    List<CreditTransaction> findByCreditId(String creditId);
    List<CreditTransaction> findByCustomerDocumentNumber(String customerDocumentNumber);
    List<CreditTransaction> findByCreditIdAndCustomerDocumentNumber(String creditId, String customerDocumentNumber);
}
