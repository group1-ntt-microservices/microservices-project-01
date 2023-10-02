package com.ntt.microservice.customers.service.exception;

/**
 * Exception thrown when a document number already exists and is expected to be unique.
 */
public class DocumentNumberAlreadyExistsException extends RuntimeException {
  public DocumentNumberAlreadyExistsException() {
    super();
  }
}
