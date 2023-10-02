package com.ntt.microservice.credits.domain.service;

import com.ntt.microservice.credits.domain.model.CreditCard;
import java.util.List;
import java.util.Optional;

/**
 * Service interface for managing CreditCard entities.
 */
public interface CreditCardService {

  /**
   * Retrieve a list of all credit cards.
   *
   * @return A list of CreditCard objects.
   */
  List<CreditCard> findAll();

  /**
   * Find a credit card by its ID.
   *
   * @param id The ID of the credit card to find.
   * @return An optional CreditCard object if found, empty otherwise.
   */
  Optional<CreditCard> findById(String id);

  /**
   * Find a credit card by its customer ID.
   *
   * @param customerId The customer ID to search for.
   * @return An optional CreditCard object if found, empty otherwise.
   */
  Optional<CreditCard> findByCustomerId(String customerId);

  /**
   * Save a new credit card.
   *
   * @param creditCard The CreditCard object to save or update.
   * @return The saved CreditCard object.
   */
  CreditCard save(CreditCard creditCard);

  /**
   * Delete a credit card by its ID.
   *
   * @param id The ID of the credit card to delete.
   */
  void deleteById(String id);

  /**
   * Check if a credit card exists for a given customer ID.
   *
   * @param customerId The customer ID to check.
   * @return True if a credit exists, false otherwise.
   */
  boolean existsByCustomerId(String customerId);

  /**
   * check if a credit card exists for a given card number.
   *
   * @param cardNumber The card number to check.
   * @return True if a credit card exists, false otherwise
   */
  boolean existsByCardNumber(String cardNumber);
}
