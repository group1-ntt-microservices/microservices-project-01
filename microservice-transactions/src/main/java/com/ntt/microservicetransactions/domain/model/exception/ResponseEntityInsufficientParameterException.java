package com.ntt.microservicetransactions.domain.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Represents the response entity for exception insufficient parameter
 */
@ControllerAdvice
public class ResponseEntityInsufficientParameterException {
    @ExceptionHandler(InsufficientParameterException.class)
    public ResponseEntity<ErrorResponse> handleInsufficientParameterException(InsufficientParameterException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
