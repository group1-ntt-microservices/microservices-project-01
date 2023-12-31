package com.ntt.microservice.customers.api.controller;

import com.ntt.microservice.customers.api.dto.response.CustomerResponseDto;
import com.ntt.microservice.customers.service.handler.CustomerHandler;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller to get all clients and list by id or document number.
 */
@Api(tags = "General Customers API", description = "Rest Controller to get all clients")
@AllArgsConstructor
@RestController
public class CustomerController {

  private CustomerHandler customerHandler;

  /**
   * Retrieves a list of all customers.
   *
   * @return ResponseEntity with the list of customers in the body.
   */
  @ApiOperation(value = "Retrieves a list of all customers.")
  @GetMapping("/")
  public ResponseEntity<List<CustomerResponseDto>> findAll() {
    return ResponseEntity.ok(customerHandler.findAll());
  }

  /**
   * Finds a customer by  ID.
   *
   * @param id The ID of the customer to be found.
   * @return ResponseEntity with the found customer in the body.
   */
  @ApiOperation(value = "Retrieves a customer by ID.")
  @GetMapping("/{id}")
  public ResponseEntity<CustomerResponseDto> findById(
      @ApiParam(value = "The ID of the customer to be found.", required = true)
      @PathVariable String id
  ) {
    return ResponseEntity.ok(customerHandler.findById(id));
  }

  /**
   * Finds a customer by document number.
   *
   * @param documentNumber The document number of the customer to be found.
   * @return ResponseEntity with the found customer in the body.
   */
  @ApiOperation(value = "Retrieves a customer by document number.")
  @GetMapping("/documentNumber/{documentNumber}")
  public ResponseEntity<CustomerResponseDto> findByDocumentNumber(
      @ApiParam(value = "The document number of the customer to be found.", required = true)
      @PathVariable String documentNumber
  ) {
    return ResponseEntity.ok(customerHandler.findByDocumentNumber(documentNumber));
  }

}
