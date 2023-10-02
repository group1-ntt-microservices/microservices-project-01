package com.ntt.microservice.credits.domain.service;

import com.ntt.microservice.credits.domain.model.Credit;
import java.util.List;
import java.util.Optional;

/**
 * Service interface for managing Credit entities.
 */
public interface CreditService {

  /**
   * Retrieve a list of all credits.
   *
   * @return A list of Credit objects.
   */
  List<Credit> findAll();

  /**
   * Retrieve a list of credits by customer ID.
   *
   * @param customerId unique identifier of the customer.
   * @return A list of Credit objects.
   */
  List<Credit> findByCustomerId(String customerId);

  /**
   * Find a credit by ID.
   *
   * @param id The ID of the credit to find.
   * @return An optional Credit object if found, empty otherwise.
   */
  Optional<Credit> findById(String id);

  /**
   * Check if a credit exists for a given customer ID.
   *
   * @param customerId The customer ID to check.
   * @return True if a credit exists, false otherwise.
   */
  boolean existsCustomerId(String customerId);

  /**
   * Save a new credit.
   *
   * @param credit The Credit object to save or update.
   * @return The saved Credit object.
   */
  Credit save(Credit credit);

  /**
   * Delete a credit by its ID.
   *
   * @param id The ID of the credit to delete.
   */
  void deleteById(String id);
}
