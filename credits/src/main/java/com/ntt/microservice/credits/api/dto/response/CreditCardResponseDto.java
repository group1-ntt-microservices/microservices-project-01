package com.ntt.microservice.credits.api.dto.response;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a response DTO for a credit card.
 */
@Getter
@Setter
public class CreditCardResponseDto {
  private String id;
  private String customerId;
  private String customerDocumentNumber;
  private String customerType;
  private String cardNumber;
  private float limitAmount;
  private float balanceAvailable;
  private float balanceDue;
  private boolean isActive;
  private Date createdAt;
}
