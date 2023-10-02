package com.ntt.microservice.credits.service.exception;

/**
 * Exception thrown when trying to pay an already paid credit.
 */
public class CreditAlreadyPaidException extends RuntimeException {
  public CreditAlreadyPaidException() {
    super();
  }
}
