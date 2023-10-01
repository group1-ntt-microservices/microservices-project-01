package com.ntt.microservice.customers.service.exception;

/**
 * Exception thrown when document numbers are different and expected to be the same.
 */
public class DifferentDocumentNumberException extends RuntimeException {
  public DifferentDocumentNumberException() {
    super();
  }
}
