package com.ntt.microservice.credits.api.dto.response;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a response DTO for a credit.
 */
@Getter
@Setter
public class CreditResponseDto {
  private String id;
  private String customerId;
  private String customerDocumentNumber;
  private String customerType;
  private float amountGranted;
  private float interest;
  private float amountInterest;
  private float amountPaid;
  private int status;
  private boolean isActive;
  private Date createdAt;
}
