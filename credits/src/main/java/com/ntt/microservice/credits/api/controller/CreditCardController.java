package com.ntt.microservice.credits.api.controller;

import com.ntt.microservice.credits.api.dto.request.CreditCardRequestDto;
import com.ntt.microservice.credits.api.dto.response.CreditCardResponseDto;
import com.ntt.microservice.credits.service.handler.CreditCardHandler;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Rest Controller for managing credit cards operations.
 */
@AllArgsConstructor
@RestController
@RequestMapping("/creditCard")
public class CreditCardController {

  private CreditCardHandler creditCardHandler;

  /**
   * Retrieves a list of all credit cards.
   *
   * @return A ResponseEntity containing a list of credit card response DTOs in the body.
   */
  @GetMapping("/")
  public ResponseEntity<List<CreditCardResponseDto>> findAll() {
    return ResponseEntity.ok(creditCardHandler.findAll());
  }

  /**
   * Retrieves a credit card by its ID.
   *
   * @param id The ID of the credit card.
   * @return A ResponseEntity containing the credit card response DTO in the body.
   */
  @GetMapping("/{id}")
  public ResponseEntity<CreditCardResponseDto> findById(@PathVariable String id) {
    return ResponseEntity.ok(creditCardHandler.findById(id));
  }

  /**
   * Retrieves a credit card by customer ID.
   *
   * @param customerId The customer ID associated with the credit card.
   * @return A ResponseEntity containing the credit card response DTO in the body.
   */
  @GetMapping("/customerId/{customerId}")
  public ResponseEntity<CreditCardResponseDto> findByCustomerId(@PathVariable String customerId) {
    return ResponseEntity.ok(creditCardHandler.findByCustomerId(customerId));
  }

  /**
   * Creates a new credit card.
   *
   * @param creditCardRequestDto The credit card request DTO.
   * @return A ResponseEntity containing the created credit card response DTO in the body.
   */
  @PostMapping("/")
  public ResponseEntity<CreditCardResponseDto> save(
      @RequestBody CreditCardRequestDto creditCardRequestDto) {
    return ResponseEntity.ok(creditCardHandler.save(creditCardRequestDto));
  }

  /**
   * Deletes a credit card by its ID.
   *
   * @param id The ID of the credit card to delete.
   * @return A ResponseEntity with no content in the body if the deletion was successful.
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<Object> deleteById(@PathVariable String id) {
    creditCardHandler.deleteById(id);
    return ResponseEntity.noContent().build();
  }

  /**
   * Updates a credit card by its ID.
   *
   * @param id                   The ID of the credit card to update.
   * @param creditCardRequestDto The credit card request DTO with updated information.
   * @return A ResponseEntity containing the updated credit card response DTO in the body.
   */
  @PutMapping("/{id}")
  public ResponseEntity<CreditCardResponseDto> update(
      @PathVariable String id,
      @RequestBody CreditCardRequestDto creditCardRequestDto
  ) {
    return ResponseEntity.ok(creditCardHandler.update(id, creditCardRequestDto));
  }
}