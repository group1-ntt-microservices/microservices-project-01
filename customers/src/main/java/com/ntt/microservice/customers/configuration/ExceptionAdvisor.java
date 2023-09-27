package com.ntt.microservice.customers.configuration;

import com.ntt.microservice.customers.service.exception.CustomerNotFoundException;
import com.ntt.microservice.customers.service.exception.DifferentDocumentNumberException;
import com.ntt.microservice.customers.service.exception.DocumentNumberAlreadyExistsException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Global exception handler for handling various exceptions in the application.
 */
@ControllerAdvice
public class ExceptionAdvisor {

  /**
   * Handles validation exceptions and returns a response with validation error details.
   *
   * @param ex The MethodArgumentNotValidException.
   * @return ResponseEntity with validation error details.
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, String>> handleValidationException(
      MethodArgumentNotValidException ex
  ) {
    Map<String, String> errors = new HashMap<>();
    ex.getFieldErrors().forEach(
        fieldError -> errors.put(fieldError.getField(), fieldError.getDefaultMessage())
    );
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
  }

  /**
   * Handles customer not found exceptions.
   *
   * @return ResponseEntity with customer not found error message.
   */
  @ExceptionHandler(CustomerNotFoundException.class)
  public ResponseEntity<Map<String, String>> handleCustomerNotFoundException() {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(Collections.singletonMap(
            ExceptionMessage.MESSAGE_KEY.getMessage(),
            ExceptionMessage.CUSTOMER_NOT_FOUND.getMessage()
        ));
  }

  /**
   * Handles document number already exists exceptions.
   *
   * @return ResponseEntity with document number already exists error message.
   */
  @ExceptionHandler(DocumentNumberAlreadyExistsException.class)
  public ResponseEntity<Map<String, String>> handleDocumentNumberAlreadyExistsException() {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(Collections.singletonMap(
            ExceptionMessage.MESSAGE_KEY.getMessage(),
            ExceptionMessage.DOCUMENT_NUMBER_ALREADY_EXISTS.getMessage()
        ));
  }

  /**
   * Handles different document number exceptions.
   *
   * @return ResponseEntity with different document number error message.
   */
  @ExceptionHandler(DifferentDocumentNumberException.class)
  public ResponseEntity<Map<String, String>> handleDifferentDocumentNumberException() {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(Collections.singletonMap(
            ExceptionMessage.MESSAGE_KEY.getMessage(),
            ExceptionMessage.DIFFERENT_DOCUMENT_NUMBER.getMessage()
        ));
  }
}
