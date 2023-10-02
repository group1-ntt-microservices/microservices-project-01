package com.ntt.microservicetransactions.domain.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Represents the response entity for limit transaction Exceeded
 */
@ControllerAdvice
public class ResponseEntityLimitTransactionExceededHandler {

    @ExceptionHandler(LimitTransactionsExceededException.class)
    public ResponseEntity<ErrorResponse> handleLimitTransactionsExceededException(LimitTransactionsExceededException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
