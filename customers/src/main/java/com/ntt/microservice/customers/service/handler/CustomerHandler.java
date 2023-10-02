package com.ntt.microservice.customers.service.handler;

import com.ntt.microservice.customers.api.dto.response.CustomerResponseDto;

import java.util.List;

/**
 * Interface for handling customer-related operations.
 */
public interface CustomerHandler {

  /**
   * Retrieves a list of all customers.
   *
   * @return List of response DTOs representing customers.
   */
  List<CustomerResponseDto> findAll();

  /**
   * Retrieves a customer by its unique identifier.
   *
   * @param id The unique identifier of the customer.
   * @return Response DTO representing the customer.
   */
  CustomerResponseDto findById(String id);

  /**
   * Retrieves a customer by its document number.
   *
   * @param documentNumber The document number of the customer.
   * @return Response DTO representing the customer.
   */
  CustomerResponseDto findByDocumentNumber(String documentNumber);
}
