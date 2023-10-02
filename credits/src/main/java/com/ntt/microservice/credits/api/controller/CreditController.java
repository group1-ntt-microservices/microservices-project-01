package com.ntt.microservice.credits.api.controller;

import com.ntt.microservice.credits.api.dto.request.CreditRequestDto;
import com.ntt.microservice.credits.api.dto.response.CreditResponseDto;
import com.ntt.microservice.credits.service.handler.CreditHandler;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;
import lombok.AllArgsConstructor;
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
 * Rest Controller for managing credit operations.
 */
@Api(tags = "Credits API", description = "Rest Controller for managing credits operations.")
@AllArgsConstructor
@RestController
@RequestMapping("/")
public class CreditController {

  private CreditHandler creditHandler;

  /**
   * Retrieves a list of all credits.
   *
   * @return A ResponseEntity containing a list of credit response DTOs in the body.
   */
  @ApiOperation("Retrieve a list of all credits")
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
  @ApiOperation("Retrieve a credit by its ID")
  @GetMapping("/{id}")
  public ResponseEntity<CreditResponseDto> findById(
      @ApiParam(value = "ID of the credit", required = true)
      @PathVariable String id
  ) {
    return ResponseEntity.ok(creditHandler.findById(id));
  }

  /**
   * Retrieves a credit by customer ID.
   *
   * @param customerId unique identifier of the customer.
   * @return A ResponseEntity containing the credit response DTO in the body.
   */
  @ApiOperation("Retrieve a credit by customer ID")
  @GetMapping("/customerId/{customerId}")
  public ResponseEntity<List<CreditResponseDto>> findByCustomerId(
      @ApiParam(value = "ID of the customer", required = true)
      @PathVariable String customerId
  ) {
    return ResponseEntity.ok(creditHandler.findByCustomerId(customerId));
  }

  /**
   * Creates a new credit.
   *
   * @param creditRequestDto The credit request DTO.
   * @return A ResponseEntity containing the created credit response DTO in the body.
   */
  @ApiOperation("Create a new credit")
  @PostMapping("/")
  public ResponseEntity<CreditResponseDto> save(
      @ApiParam(value = "Credit request DTO", required = true, name = "Credit Request Dto")
      @Validated @RequestBody CreditRequestDto creditRequestDto) {
    return ResponseEntity.ok(creditHandler.save(creditRequestDto));
  }

  /**
   * Deletes a credit by its ID.
   *
   * @param id The ID of the credit to delete.
   * @return A ResponseEntity with no content in the body if the deletion was successful.
   */
  @ApiOperation("Delete a credit by its ID")
  @DeleteMapping("/{id}")
  public ResponseEntity<Object> deleteById(
      @ApiParam(value = "ID of the credit", required = true)
      @PathVariable String id
  ) {
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
  @ApiOperation("Update a credit by its ID")
  @PutMapping("/{id}")
  public ResponseEntity<CreditResponseDto> update(
      @ApiParam(value = "ID of the credit", required = true)
      @PathVariable String id,
      @ApiParam(value = "Credit request DTO with updated information", required = true)
      @Validated @RequestBody CreditRequestDto creditRequestDto
  ) {
    return ResponseEntity.ok(creditHandler.update(id, creditRequestDto));
  }
}
