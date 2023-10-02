package com.ntt.microservice.credits.service.exception;

/**
 * Exception thrown when a customer is not found.
 */
public class CustomerNotFoundException extends RuntimeException {
  public CustomerNotFoundException() {
    super();
  }
}
