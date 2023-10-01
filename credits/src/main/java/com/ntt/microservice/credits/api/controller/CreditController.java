package com.ntt.microservice.credits.api.controller;

import com.ntt.microservice.credits.api.dto.request.CreditRequestDto;
import com.ntt.microservice.credits.api.dto.response.CreditResponseDto;
import com.ntt.microservice.credits.service.handler.CreditHandler;
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
 * Rest Controller for managing credit operations.
 */
@AllArgsConstructor
@RestController
@RequestMapping("/credit")
public class CreditController {

  private CreditHandler creditHandler;

  /**
   * Retrieves a list of all credits.
   *
   * @return A ResponseEntity containing a list of credit response DTOs in the body.
   */
  @GetMapping("/")
  public ResponseEntity<List<CreditResponseDto>> findAll() {
    return ResponseEntity.ok(creditHandler.findAll());
  }

  /**
   * Retrieves a credit by its ID.
   *
   * @param id The ID of the credit.
   * @return A ResponseEntity containing the credit response DTO in the body.
   */
  @GetMapping("/{id}")
  public ResponseEntity<CreditResponseDto> findById(@PathVariable String id) {
    return ResponseEntity.ok(creditHandler.findById(id));
  }

  /**
   * Creates a new credit.
   *
   * @param creditRequestDto The credit request DTO.
   * @return A ResponseEntity containing the created credit response DTO in the body.
   */
  @PostMapping("/")
  public ResponseEntity<CreditResponseDto> save(@RequestBody CreditRequestDto creditRequestDto) {
    return ResponseEntity.ok(creditHandler.save(creditRequestDto));
  }

  /**
   * Deletes a credit by its ID.
   *
   * @param id The ID of the credit to delete.
   * @return A ResponseEntity with no content in the body if the deletion was successful.
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<Object> deleteById(@PathVariable String id) {
    creditHandler.deleteById(id);
    return ResponseEntity.noContent().build();
  }

  /**
   * Updates a credit by its ID.
   *
   * @param id               The ID of the credit to update.
   * @param creditRequestDto The credit request DTO with updated information.
   * @return A ResponseEntity containing the updated credit response DTO in the body.
   */
  @PutMapping("/{id}")
  public ResponseEntity<CreditResponseDto> update(
      @PathVariable String id,
      @RequestBody CreditRequestDto creditRequestDto
  ) {
    return ResponseEntity.ok(creditHandler.update(id, creditRequestDto));
  }
}
