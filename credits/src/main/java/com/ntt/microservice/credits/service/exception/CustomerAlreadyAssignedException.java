package com.ntt.microservice.credits.service.exception;

/**
 * Exception thrown when a customer is already assigned to a credit.
 */
public class CustomerAlreadyAssignedException extends RuntimeException {
  public CustomerAlreadyAssignedException() {
    super();
  }
}
