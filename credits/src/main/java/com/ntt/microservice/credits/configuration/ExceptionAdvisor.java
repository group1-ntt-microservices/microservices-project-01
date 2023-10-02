package com.ntt.microservice.credits.configuration;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import com.ntt.microservice.credits.service.exception.CardNumberAlreadyExistsException;
import com.ntt.microservice.credits.service.exception.CreditAlreadyPaidException;
import com.ntt.microservice.credits.service.exception.CreditNotFoundException;
import com.ntt.microservice.credits.service.exception.CreditServiceInternalErrorException;
import com.ntt.microservice.credits.service.exception.CustomerAlreadyAssignedException;
import com.ntt.microservice.credits.service.exception.CustomerFoundIsNullException;
import com.ntt.microservice.credits.service.exception.CustomerNotFoundException;
import com.ntt.microservice.credits.service.exception.ImpossibleChangeValueException;
import com.ntt.microservice.credits.service.exception.SomeAmountIsIncorrectException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Global exception handler for the application.
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
   * Handles exceptions when a card number already exists.
   *
   * @return ResponseEntity with the card number already exists error message.
   */
  @ExceptionHandler(CardNumberAlreadyExistsException.class)
  public ResponseEntity<Map<String, String>> handleCardNumberAlreadyExistsException() {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(Collections.singletonMap(
            ExceptionMessage.MESSAGE_KEY.getMessage(),
            ExceptionMessage.CARD_NUMBER_ALREADY_EXISTS.getMessage()
        ));
  }

  /**
   * Handles exceptions when a credit is already paid.
   *
   * @return ResponseEntity with the credit already paid error message.
   */
  @ExceptionHandler(CreditAlreadyPaidException.class)
  public ResponseEntity<Map<String, String>> handleCreditAlreadyPaidException() {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(Collections.singletonMap(
            ExceptionMessage.MESSAGE_KEY.getMessage(),
            ExceptionMessage.CREDIT_ALREADY_PAID.getMessage()
        ));
  }

  /**
   * Handles exceptions when a credit is not found.
   *
   * @return ResponseEntity with the credit not found error message.
   */
  @ExceptionHandler(CreditNotFoundException.class)
  public ResponseEntity<Map<String, String>> handleCreditNotFoundException() {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(Collections.singletonMap(
            ExceptionMessage.MESSAGE_KEY.getMessage(),
            ExceptionMessage.CREDIT_NOT_FOUND.getMessage()
        ));
  }

  /**
   * Handles exceptions when there is an internal error in the credit service.
   *
   * @return ResponseEntity with the credit service error message.
   */
  @ExceptionHandler(CreditServiceInternalErrorException.class)
  public ResponseEntity<Map<String, String>> handleCreditServiceInternalErrorException() {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(Collections.singletonMap(
            ExceptionMessage.MESSAGE_KEY.getMessage(),
            ExceptionMessage.CREDIT_SERVICE_ERROR.getMessage()
        ));
  }

  /**
   * Handles exceptions when a customer is already assigned.
   *
   * @return ResponseEntity with the customer already assigned error message.
   */
  @ExceptionHandler(CustomerAlreadyAssignedException.class)
  public ResponseEntity<Map<String, String>> handleCustomerAlreadyAssignedException() {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(Collections.singletonMap(
            ExceptionMessage.MESSAGE_KEY.getMessage(),
            ExceptionMessage.CUSTOMER_ALREADY_ASSIGNED.getMessage()
        ));
  }

  /**
   * Handles exceptions when a customer found is null.
   *
   * @return ResponseEntity with the customer found is null error message.
   */
  @ExceptionHandler(CustomerFoundIsNullException.class)
  public ResponseEntity<Map<String, String>> handleCustomerFoundIsNullException() {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(Collections.singletonMap(
            ExceptionMessage.MESSAGE_KEY.getMessage(),
            ExceptionMessage.CUSTOMER_FOUND_IS_NULL.getMessage()
        ));
  }

  /**
   * Handles exceptions when a customer is not found.
   *
   * @return ResponseEntity with the customer not found error message.
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
   * Handles exceptions when it's impossible to change a value.
   *
   * @return ResponseEntity with the impossible change value error message.
   */
  @ExceptionHandler(ImpossibleChangeValueException.class)
  public ResponseEntity<Map<String, String>> handleImpossibleChangeValueException() {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(Collections.singletonMap(
            ExceptionMessage.MESSAGE_KEY.getMessage(),
            ExceptionMessage.IMPOSSIBLE_CHANGE_VALUE.getMessage()
        ));
  }

  /**
   * Handles exceptions when some amount is incorrect.
   *
   * @return ResponseEntity with some amount is incorrect error message.
   */
  @ExceptionHandler(SomeAmountIsIncorrectException.class)
  public ResponseEntity<Map<String, String>> handleSomeAmountIsIncorrectException() {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(Collections.singletonMap(
            ExceptionMessage.MESSAGE_KEY.getMessage(),
            ExceptionMessage.SOME_AMOUNT_IS_INCORRECT.getMessage()
        ));
  }
}
