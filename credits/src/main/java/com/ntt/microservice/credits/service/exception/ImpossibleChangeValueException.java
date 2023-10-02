package com.ntt.microservice.credits.service.exception;

/**
 * Exception thrown when attempting an impossible value change.
 */
public class ImpossibleChangeValueException extends RuntimeException {
  public ImpossibleChangeValueException() {
    super();
  }
}
