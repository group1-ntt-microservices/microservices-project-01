package com.ntt.microservice.credits.service.exception;

/**
 * Exception thrown when there is an internal error in the credit service.
 */
public class CreditServiceInternalErrorException extends RuntimeException {
  public CreditServiceInternalErrorException() {
    super();
  }
}
