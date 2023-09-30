package com.ntt.microservice.credits.api.dto.request;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents a request DTO for creating or updating a credit card.
 */
@Getter
@Setter
public class CreditCardRequestDto {
  private String customerId;
  private String cardNumber;
  private float limitAmount;
  private float balanceAvailable;
  private float balanceDue;
}
