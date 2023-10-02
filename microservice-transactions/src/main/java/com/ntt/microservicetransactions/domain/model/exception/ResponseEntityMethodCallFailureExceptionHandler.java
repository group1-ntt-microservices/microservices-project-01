package com.ntt.microservicetransactions.domain.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Represents the response entity for exception method call failure
 */
@ControllerAdvice
public class ResponseEntityMethodCallFailureExceptionHandler {
    @ExceptionHandler(MethodCallFailureException.class)
    public ResponseEntity<ErrorResponse> handleMethodCallFailureException(MethodCallFailureException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
