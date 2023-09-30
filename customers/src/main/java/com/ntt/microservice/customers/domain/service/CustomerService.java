package com.ntt.microservice.customers.domain.service;

import com.ntt.microservice.customers.domain.model.Customer;
import java.util.List;
import java.util.Optional;

/**
 * A service interface for managing customers.
 */
public interface CustomerService {

  /**
   * Retrieves a list of all customers.
   *
   * @return A list of customers.
   */
  List<Customer> findAll();

  /**
   * Retrieves a customer by their ID.
   *
   * @param id The unique identifier of the customer to retrieve.
   * @return An Optional containing the customer if found, or an empty Optional if not found.
   */
  Optional<Customer> findById(String id);

  /**
   * Retrieves a customer by their document number.
   *
   * @param documentNumber The document number of the customer to retrieve.
   * @return An Optional containing the customer if found, or an empty Optional if not found.
   */
  Optional<Customer> findByDocumentNumber(String documentNumber);
}
