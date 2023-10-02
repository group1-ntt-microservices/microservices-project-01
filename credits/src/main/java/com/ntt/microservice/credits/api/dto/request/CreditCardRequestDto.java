package com.ntt.microservice.credits.api.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a request DTO for creating or updating a credit card.
 */
@Getter
@Setter
public class CreditCardRequestDto {

  @NotBlank(message = "Customer ID cannot be blank.")
  private String customerId;

  @NotBlank(message = "Card number cannot be blank.")
  private String cardNumber;

  @PositiveOrZero(message = "Limit amount cannot be negative.")
  private float limitAmount;

  @PositiveOrZero(message = "Balance available cannot be negative.")
  private float balanceAvailable;

  @PositiveOrZero(message = "Balance due cannot be negative.")
  private float balanceDue;
}
