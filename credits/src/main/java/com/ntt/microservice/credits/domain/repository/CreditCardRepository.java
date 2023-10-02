package com.ntt.microservice.credits.domain.repository;

import com.ntt.microservice.credits.domain.model.CreditCard;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for managing CreditCard entities in the database.
 */
@Repository
public interface CreditCardRepository extends JpaRepository<CreditCard, String> {
  boolean existsByCustomerId(String customerId);

  boolean existsByCardNumber(String cardNumber);

  Optional<CreditCard> findByCustomerId(String customerId);
}
