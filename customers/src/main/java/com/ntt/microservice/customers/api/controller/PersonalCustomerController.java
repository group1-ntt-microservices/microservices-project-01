package com.ntt.microservice.customers.api.controller;

import com.ntt.microservice.customers.api.dto.request.PersonalCustomerRequestDto;
import com.ntt.microservice.customers.api.dto.response.PersonalCustomerResponseDto;
import com.ntt.microservice.customers.service.handler.PersonalCustomerHandler;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for managing personal customers.
 */
@AllArgsConstructor
@RestController
@RequestMapping("/personal")
public class PersonalCustomerController {

  private PersonalCustomerHandler personalCustomerHandler;

  /**
   * Retrieves all personal customers.
   *
   * @return ResponseEntity with a list of personal customers in the body.
   */
  @GetMapping("/")
  public ResponseEntity<List<PersonalCustomerResponseDto>> findAll() {
    return ResponseEntity.ok(personalCustomerHandler.findAll());
  }

  /**
   * Finds a personal customer by ID.
   *
   * @param id The ID of the personal customer to be found.
   * @return ResponseEntity with the found personal customer in the body.
   */
  @GetMapping("/{id}")
  public ResponseEntity<PersonalCustomerResponseDto> findById(@PathVariable String id) {
    return ResponseEntity.ok(personalCustomerHandler.findById(id));
  }

  /**
   * Finds a personal customer by document number.
   *
   * @param documentNumber The document number of the personal customer to be found.
   * @return ResponseEntity with the found personal customer in the body.
   */
  @GetMapping("/documentNumber/{documentNumber}")
  public ResponseEntity<PersonalCustomerResponseDto> findByDocumentNumber(
      @PathVariable String documentNumber
  ) {
    return ResponseEntity.ok(personalCustomerHandler.findByDocumentNumber(documentNumber));
  }

  /**
   * Creates a new personal customer.
   *
   * @param personalCustomer The request DTO containing personal customer information.
   * @return ResponseEntity with the created personal customer in the body.
   */
  @PostMapping("/")
  public ResponseEntity<PersonalCustomerResponseDto> save(
      @Validated @RequestBody PersonalCustomerRequestDto personalCustomer
  ) {
    return ResponseEntity.ok(personalCustomerHandler.save(personalCustomer));
  }

  /**
   * Updates an existing personal customer.
   *
   * @param id The ID of the personal customer to be updated.
   * @param personalCustomer The request DTO containing updated personal customer information.
   * @return ResponseEntity with the updated personal customer in the body.
   */
  @PutMapping("/{id}")
  public ResponseEntity<PersonalCustomerResponseDto> update(
      @PathVariable String id,
      @Validated @RequestBody PersonalCustomerRequestDto personalCustomer
  ) {
    return ResponseEntity.ok(personalCustomerHandler.update(id, personalCustomer));
  }

  /**
   * Delete a personal customer by ID.
   *
   * @param id The ID of the personal customer to be deleted.
   * @return ResponseEntity with no content.
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<HttpStatus> deleteById(@PathVariable String id) {
    personalCustomerHandler.deleteById(id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }
}
