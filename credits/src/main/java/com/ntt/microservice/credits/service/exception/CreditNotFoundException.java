package com.ntt.microservice.credits.service.exception;

/**
 * Exception thrown when a credit is not found.
 */
public class CreditNotFoundException extends RuntimeException {
  public CreditNotFoundException() {
    super();
  }
}
