package com.ntt.microservice.customers.configuration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enum that defines exception messages.
 */
@AllArgsConstructor
@Getter
public enum ExceptionMessage {
  MESSAGE_KEY("Error"),
  CUSTOMER_NOT_FOUND("Customer not found"),
  DOCUMENT_NUMBER_ALREADY_EXISTS("Document number already exists"),
  DIFFERENT_DOCUMENT_NUMBER("Impossible to change document number");

  private final String message;
}
