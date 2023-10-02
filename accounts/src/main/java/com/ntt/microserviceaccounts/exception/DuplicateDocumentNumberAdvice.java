package com.ntt.microserviceaccounts.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * Custom exception handler for handling duplicate document number errors.
 */
@ControllerAdvice
public class DuplicateDocumentNumberAdvice {
    /**
     * Exception handler method for DuplicateDocumetNumberException.
     * Handles the exception and returns a response with an error message.
     *
     * @param exception The DuplicateDocumetNumberException instance.
     * @return A Map containing the error message.
     */
    @ResponseBody
    @ExceptionHandler(DuplicateDocumetNumberException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> exceptionHandler(DuplicateDocumetNumberException exception){
        Map<String, String> err = new HashMap<>();
        err.put("errorMessage", exception.getMessage());
        return err;
    }
}
