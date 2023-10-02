package com.ntt.microserviceaccounts.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * Advice class to handle exceptions related to customer not found errors.
 */
@ControllerAdvice
public class CustomerNotFoundAdvice {
    /**
     * Handles CustomerNotFoundException and returns an appropriate error response.
     *
     * @param exception The exception object.
     * @return A map containing the error message.
     */
    @ResponseBody
    @ExceptionHandler(CustomerNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> exceptionHandler(CustomerNotFoundException exception){
        Map<String, String> err = new HashMap<>();
        err.put("errorMessage", exception.getMessage());
        return err;
    }
}
