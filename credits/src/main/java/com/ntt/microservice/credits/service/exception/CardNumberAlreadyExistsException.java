package com.ntt.microservice.credits.service.exception;

/**
 * Exception thrown when a credit card with the same number already exists.
 */
public class CardNumberAlreadyExistsException extends RuntimeException {
  public CardNumberAlreadyExistsException() {
    super();
  }
}
