package com.ntt.microservice.customers.service.handler;

import java.util.List;

/**
 * Interface for handling base customer operations.
 *
 * @param <T> Type of the customer response DTO.
 * @param <U> Type of the customer request DTO.
 */
public interface BaseCustomerHandler<T, U> {

  List<T> findAll();

  T findById(String id);

  T findByDocumentNumber(String documentNumber);

  T save(U customer);

  T update(String id, U customer);

  void deleteById(String id);
}
