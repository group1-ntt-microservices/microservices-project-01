package com.ntt.microservice.credits.domain.repository;

import com.ntt.microservice.credits.domain.model.Credit;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for managing Credit entities in the database.
 */
@Repository
public interface CreditRepository extends JpaRepository<Credit, String> {

  /**
   * Check if a credit with the given customer ID exists.
   *
   * @param customerId The unique identifier of the customer.
   * @return true if a credit with the given customer ID exists, false otherwise.
   */
  boolean existsByCustomerId(String customerId);

  /**
   * Retrieve a credits by its customer ID.
   *
   * @param customerId The unique identifier of the customer
   * @return List of Credit for the given customer
   */
  List<Credit> findByCustomerId(String customerId);
}
