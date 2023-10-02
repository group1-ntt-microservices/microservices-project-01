package com.ntt.microservice.credits.service.handler;

import com.ntt.microservice.credits.api.dto.request.CreditCardRequestDto;
import com.ntt.microservice.credits.api.dto.response.CreditCardResponseDto;
import java.util.List;

/**
 * Interface for handling Credit card related operations.
 */
public interface CreditCardHandler {

  /**
   * Retrieves a list of all credit cards.
   *
   * @return A list of credit card response DTOs.
   */
  List<CreditCardResponseDto> findAll();

  /**
   * Retrieves a credit card by its ID.
   *
   * @param id The ID of the credit card.
   * @return The credit card response DTO.
   */
  CreditCardResponseDto findById(String id);

  /**
   * Retrieves a credit card by customer ID.
   *
   * @param customerId The ID of the customer.
   * @return The credit card response DTO.
   */
  CreditCardResponseDto findByCustomerId(String customerId);

  /**
   * Creates a new credit card.
   *
   * @param creditCardRequestDto The credit card request DTO.
   * @return The created credit card response DTO.
   */
  CreditCardResponseDto save(CreditCardRequestDto creditCardRequestDto);

  /**
   * Delete a credit card by its ID.
   *
   * @param id The ID of the credit card to delete.
   */
  void deleteById(String id);

  /**
   * Updates a credit card by its ID.
   *
   * @param id                   The ID of the credit card to update.
   * @param creditCardRequestDto The credit card request DTO with updated information.
   * @return The updated credit card response DTO.
   */
  CreditCardResponseDto update(String id, CreditCardRequestDto creditCardRequestDto);
}
