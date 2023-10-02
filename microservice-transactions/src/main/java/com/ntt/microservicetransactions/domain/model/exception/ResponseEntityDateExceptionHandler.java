package com.ntt.microservicetransactions.domain.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Represents the response entity for exception date incorrect
 */
@ControllerAdvice
public class ResponseEntityDateExceptionHandler {

    @ExceptionHandler(InvalidDateException.class)
    public ResponseEntity<ErrorResponse> handleInvalidDateException(InvalidDateException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
