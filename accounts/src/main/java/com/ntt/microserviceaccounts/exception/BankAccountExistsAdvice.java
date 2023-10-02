package com.ntt.microserviceaccounts.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * Global exception handler for BankAccountExistsException.
 */
@ControllerAdvice
public class BankAccountExistsAdvice {
    /**
     * Handles BankAccountExistsException and returns a custom error response.
     *
     * @param exception The BankAccountExistsException instance.
     * @return A Map containing the error message.
     */
    @ResponseBody
    @ExceptionHandler(BankAccountExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> exceptionHandler(BankAccountExistsException exception){
        Map<String, String> err = new HashMap<>();
        err.put("errorMessage", exception.getMessage());
        return err;
    }
}
