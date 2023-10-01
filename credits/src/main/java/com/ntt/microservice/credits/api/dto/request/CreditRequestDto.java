package com.ntt.microservice.credits.api.dto.request;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents a request DTO for creating or updating a credit.
 */
@Getter
@Setter
public class CreditRequestDto {
  private String customerId;
  private float amountGranted;
  private float interest;
  private float amountInterest;
  private float amountPaid;
}
