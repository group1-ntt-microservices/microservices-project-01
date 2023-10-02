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

  /**
   * Check if a credit card with the given customer ID exists.
   *
   * @param customerId The ID of the customer.
   * @return true if a credit card with the given customer ID exists, false otherwise.
   */
  boolean existsByCustomerId(String customerId);

  /**
   * Check if a credit card with the given card number exists.
   *
   * @param cardNumber The card number.
   * @return true if a credit card with the given card number exists, false otherwise.
   */
  boolean existsByCardNumber(String cardNumber);

  /**
   * Retrieve a credit card by customer ID.
   *
   * @param customerId The unique identifier of the customer.
   * @return An Optional containing the CreditCard object, or empty if not found.
   */
  Optional<CreditCard> findByCustomerId(String customerId);
}
