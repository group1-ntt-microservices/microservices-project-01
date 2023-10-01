package com.ntt.microservice.customers.api.controller;

import com.ntt.microservice.customers.api.dto.request.BusinessCustomerRequestDto;
import com.ntt.microservice.customers.api.dto.response.BusinessCustomerResponseDto;
import com.ntt.microservice.customers.service.handler.BusinessCustomerHandler;
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
 * Controller for managing business customers.
 */
@AllArgsConstructor
@RestController
@RequestMapping("/business")
public class BusinessCustomerController {

  private BusinessCustomerHandler businessCustomerHandler;

  /**
   * Retrieves all business customers.
   *
   * @return ResponseEntity with a list of business customers in the body.
   */
  @GetMapping("/")
  public ResponseEntity<List<BusinessCustomerResponseDto>> findAll() {
    return ResponseEntity.ok(businessCustomerHandler.findAll());
  }

  /**
   * Finds a business customer by ID.
   *
   * @param id The ID of the business customer to be retrieved.
   * @return ResponseEntity with the found business customer in the body if it exists;
   *        otherwise, returns 404 not found.
   */
  @GetMapping("/{id}")
  public ResponseEntity<BusinessCustomerResponseDto> findById(@PathVariable String id) {
    return ResponseEntity.ok(businessCustomerHandler.findById(id));
  }

  /**
   * Finds a business customer by document number.
   *
   * @param documentNumber The document number of the business customer to be found.
   * @return ResponseEntity with the found business customer in the body if it exists;
   *        otherwise, returns 404 not found.
   */
  @GetMapping("/documentNumber/{documentNumber}")
  public ResponseEntity<BusinessCustomerResponseDto> findByDocumentNumber(
      @PathVariable String documentNumber
  ) {
    return ResponseEntity.ok(businessCustomerHandler.findByDocumentNumber(documentNumber));
  }

  /**
   * Creates a new business customer.
   *
   * @param businessCustomerRequestDto The request DTO containing business customer information.
   * @return ResponseEntity with the created business customer in the body.
   */
  @PostMapping("/")
  public ResponseEntity<BusinessCustomerResponseDto> save(
      @Validated @RequestBody BusinessCustomerRequestDto businessCustomerRequestDto
  ) {
    return ResponseEntity.ok(businessCustomerHandler.save(businessCustomerRequestDto));
  }

  /**
   * Updates an existing business customer.
   *
   * @param id The ID of the business customer to be updated.
   * @param businessCustomerRequestDto The request DTO containing updated business customer
   *                                   information.
   * @return ResponseEntity with the updated business customer in the body if it exists;
   *        otherwise, returns 404 not found.
   */
  @PutMapping("/{id}")
  public ResponseEntity<BusinessCustomerResponseDto> update(
      @PathVariable String id,
      @Validated @RequestBody BusinessCustomerRequestDto businessCustomerRequestDto
  ) {
    return ResponseEntity.ok(businessCustomerHandler.update(id, businessCustomerRequestDto));
  }

  /**
   * Delete a business customer by ID.
   *
   * @param id The ID of the business customer to be deleted.
   * @return ResponseEntity with no content.
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<HttpStatus> deleteById(@PathVariable String id) {
    businessCustomerHandler.deleteById(id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }
}
