package com.ntt.microserviceaccounts.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * Advice class to handle BankAccountNotFoundException and return appropriate error response.
 */
@ControllerAdvice
public class BankAccountNotFoundAdvice {
    /**
     * Handles BankAccountNotFoundException and returns a JSON response with the error message.
     *
     * @param exception The exception to handle.
     * @return A Map containing the error message.
     */
    @ResponseBody
    @ExceptionHandler(BankAccountNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> exceptionHandler(BankAccountNotFoundException exception){
        Map<String, String> err = new HashMap<>();
        err.put("errorMessage", exception.getMessage());
        return err;
    }
}
