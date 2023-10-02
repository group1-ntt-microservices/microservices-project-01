package com.ntt.microservice.credits.api.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreditCardRequestDto {
  private String customerId;
  private String cardNumber;
  private float limitAmount;
  private float balanceAvailable;
  private float balanceDue;
}
