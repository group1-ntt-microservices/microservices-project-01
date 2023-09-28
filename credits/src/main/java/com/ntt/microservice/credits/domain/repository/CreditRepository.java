package com.ntt.microservice.credits.domain.repository;

import com.ntt.microservice.credits.domain.model.Credit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for managing Credit entities in the database.
 */
@Repository
public interface CreditRepository extends JpaRepository<Credit, String> {
  boolean existsByCustomerId(String customerId);
}
