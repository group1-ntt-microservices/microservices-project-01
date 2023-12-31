package com.ntt.microservice.customers.api.controller;

import com.ntt.microservice.customers.api.dto.request.BusinessCustomerRequestDto;
import com.ntt.microservice.customers.api.dto.response.BusinessCustomerResponseDto;
import com.ntt.microservice.customers.service.handler.BusinessCustomerHandler;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
@Api(tags = "Business Customers API",
    description = "Rest Controller for managing business customers")
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
  @ApiOperation(value = "Retrieves all business customers.")
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
  @ApiOperation(value = "Retrieves a business customer by ID.")
  @GetMapping("/{id}")
  public ResponseEntity<BusinessCustomerResponseDto> findById(
      @ApiParam(value = "The ID of the business customer to be found.", required = true)
      @PathVariable String id
  ) {
    return ResponseEntity.ok(businessCustomerHandler.findById(id));
  }

  /**
   * Finds a business customer by document number.
   *
   * @param documentNumber The document number of the business customer to be found.
   * @return ResponseEntity with the found business customer in the body if it exists;
   *        otherwise, returns 404 not found.
   */
  @ApiOperation(value = "Retrieves a business customer by document number.")
  @GetMapping("/documentNumber/{documentNumber}")
  public ResponseEntity<BusinessCustomerResponseDto> findByDocumentNumber(
      @ApiParam(value = "The document number of the business customer to be found.",
          required = true)
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
  @ApiOperation(value = "Creates a new business customer.")
  @PostMapping("/")
  public ResponseEntity<BusinessCustomerResponseDto> save(
      @ApiParam(value = "The request DTO containing business customer information.",
          required = true)
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
  @ApiOperation(value = "Updates an existing business customer.")
  @PutMapping("/{id}")
  public ResponseEntity<BusinessCustomerResponseDto> update(
      @ApiParam(value = "The ID of the business customer to be updated.", required = true)
      @PathVariable String id,
      @ApiParam(value = "The request DTO containing updated business customer information.",
          required = true)
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
  @ApiOperation(value = "Delete a business customer by ID.")
  @DeleteMapping("/{id}")
  public ResponseEntity<HttpStatus> deleteById(
      @ApiParam(value = "The ID of the business customer to be deleted.", required = true)
      @PathVariable String id
  ) {
    businessCustomerHandler.deleteById(id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }
}
