package com.ntt.microservice.credits.service.handler;

import com.ntt.microservice.credits.api.dto.request.CreditRequestDto;
import com.ntt.microservice.credits.api.dto.response.CreditResponseDto;
import java.util.List;

/**
 * Interface for handling Credit related operations.
 */
public interface CreditHandler {

  /**
   * Retrieves a list of all credits.
   *
   * @return A list of CreditResponseDto objects.
   */
  List<CreditResponseDto> findAll();

  /**
   * Retrieves a credit by its ID.
   *
   * @param id The unique identifier of the credit.
   * @return The CreditResponseDto object if found, or null otherwise.
   */
  CreditResponseDto findById(String id);

  /**
   * Saves a new credit based on the provided CreditRequestDto.
   *
   * @param creditRequestDto The CreditRequestDto containing credit details.
   * @return The created CreditResponseDto.
   */
  CreditResponseDto save(CreditRequestDto creditRequestDto);

  /**
   * Delete a credit by its ID.
   *
   * @param id The unique identifier of the credit to delete.
   */
  void deleteById(String id);

  /**
   * Updates an existing credit with the provided CreditRequestDto.
   *
   * @param id               The unique identifier of the credit to update.
   * @param creditRequestDto The CreditRequestDto containing updated credit details.
   * @return The updated CreditResponseDto.
   */
  CreditResponseDto update(String id, CreditRequestDto creditRequestDto);
}
