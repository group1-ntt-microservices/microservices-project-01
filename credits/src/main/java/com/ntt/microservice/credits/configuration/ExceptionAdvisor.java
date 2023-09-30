package com.ntt.microservice.credits.configuration;

import java.util.Collections;
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
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Global exception handler for the application.
 */
@ControllerAdvice
public class ExceptionAdvisor {

  @ExceptionHandler(CardNumberAlreadyExistsException.class)
  public ResponseEntity<Map<String, String>> handleCardNumberAlreadyExistsException() {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(Collections.singletonMap(
            ExceptionMessage.MESSAGE_KEY.getMessage(),
            ExceptionMessage.CARD_NUMBER_ALREADY_EXISTS.getMessage()
        ));
  }

  @ExceptionHandler(CreditAlreadyPaidException.class)
  public ResponseEntity<Map<String, String>> handleCreditAlreadyPaidException() {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(Collections.singletonMap(
            ExceptionMessage.MESSAGE_KEY.getMessage(),
            ExceptionMessage.CREDIT_ALREADY_PAID.getMessage()
        ));
  }

  @ExceptionHandler(CreditNotFoundException.class)
  public ResponseEntity<Map<String, String>> handleCreditNotFoundException() {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(Collections.singletonMap(
            ExceptionMessage.MESSAGE_KEY.getMessage(),
            ExceptionMessage.CREDIT_NOT_FOUND.getMessage()
        ));
  }

  @ExceptionHandler(CreditServiceInternalErrorException.class)
  public ResponseEntity<Map<String, String>> handleCreditServiceInternalErrorException() {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(Collections.singletonMap(
            ExceptionMessage.MESSAGE_KEY.getMessage(),
            ExceptionMessage.CREDIT_SERVICE_ERROR.getMessage()
        ));
  }

  @ExceptionHandler(CustomerAlreadyAssignedException.class)
  public ResponseEntity<Map<String, String>> handleCustomerAlreadyAssignedException() {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(Collections.singletonMap(
            ExceptionMessage.MESSAGE_KEY.getMessage(),
            ExceptionMessage.CUSTOMER_ALREADY_ASSIGNED.getMessage()
        ));
  }

  @ExceptionHandler(CustomerFoundIsNullException.class)
  public ResponseEntity<Map<String, String>> handleCustomerFoundIsNullException() {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(Collections.singletonMap(
            ExceptionMessage.MESSAGE_KEY.getMessage(),
            ExceptionMessage.CUSTOMER_FOUND_IS_NULL.getMessage()
        ));
  }

  @ExceptionHandler(CustomerNotFoundException.class)
  public ResponseEntity<Map<String, String>> handleCustomerNotFoundException() {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(Collections.singletonMap(
            ExceptionMessage.MESSAGE_KEY.getMessage(),
            ExceptionMessage.CUSTOMER_NOT_FOUND.getMessage()
        ));
  }

  @ExceptionHandler(ImpossibleChangeValueException.class)
  public ResponseEntity<Map<String, String>> handleImpossibleChangeValueException() {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(Collections.singletonMap(
            ExceptionMessage.MESSAGE_KEY.getMessage(),
            ExceptionMessage.IMPOSSIBLE_CHANGE_VALUE.getMessage()
        ));
  }

  @ExceptionHandler(SomeAmountIsIncorrectException.class)
  public ResponseEntity<Map<String, String>> handleSomeAmountIsIncorrectException() {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(Collections.singletonMap(
            ExceptionMessage.MESSAGE_KEY.getMessage(),
            ExceptionMessage.SOME_AMOUNT_IS_INCORRECT.getMessage()
        ));
  }
}
