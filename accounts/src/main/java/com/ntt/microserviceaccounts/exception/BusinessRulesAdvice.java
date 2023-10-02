package com.ntt.microserviceaccounts.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * Global exception handler for BusinessRulesException in the application.
 */
@ControllerAdvice
public class BusinessRulesAdvice {
    /**
     * Handles BusinessRulesException and returns a custom error message in the response body.
     *
     * @param exception The BusinessRulesException to be handled.
     * @return A map containing the custom error message.
     */
    @ResponseBody
    @ExceptionHandler(BusinessRulesException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> exceptionHandler(BusinessRulesException exception){
        Map<String, String> err = new HashMap<>();
        err.put("errorMessage", exception.getMessage());
        return err;
    }
}
