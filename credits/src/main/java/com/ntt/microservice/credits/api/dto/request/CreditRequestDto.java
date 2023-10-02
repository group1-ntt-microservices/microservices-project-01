package com.ntt.microservice.credits.api.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a request DTO for creating or updating a credit.
 */
@Getter
@Setter
public class CreditRequestDto {

  @NotBlank(message = "Customer ID cannot be blank.")
  private String customerId;

  @PositiveOrZero(message = "Limit amount cannot be negative.")
  private float amountGranted;

  @PositiveOrZero(message = "Interest cannot be negative.")
  private float interest;

  @PositiveOrZero(message = "Amount interest cannot be negative.")
  private float amountInterest;

  @PositiveOrZero(message = "Amount paid cannot be negative.")
  private float amountPaid;
}
