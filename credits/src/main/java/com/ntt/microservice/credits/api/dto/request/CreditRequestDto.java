package com.ntt.microservice.credits.api.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreditRequestDto {
  private String customerId;
  private float amountGranted;
  private float interest;
  private float amountInterest;
  private float amountPaid;
}
