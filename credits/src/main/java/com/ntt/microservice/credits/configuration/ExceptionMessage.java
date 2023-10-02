package com.ntt.microservice.credits.configuration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enum of messages for exceptions.
 */
@AllArgsConstructor
@Getter
public enum ExceptionMessage {
  MESSAGE_KEY("Error"),
  CARD_NUMBER_ALREADY_EXISTS("Card number already exists"),
  CREDIT_ALREADY_PAID("Credit already paid"),
  CREDIT_NOT_FOUND("Credit not found"),
  CREDIT_SERVICE_ERROR("Credit service error"),
  CUSTOMER_ALREADY_ASSIGNED("Customer already assigned"),
  CUSTOMER_FOUND_IS_NULL("Customer found is null"),
  CUSTOMER_NOT_FOUND("Customer not found"),
  IMPOSSIBLE_CHANGE_VALUE("Impossible to change value"),
  SOME_AMOUNT_IS_INCORRECT("Some amount is incorrect");

  private final String message;
}
