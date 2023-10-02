package com.ntt.microserviceaccounts.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;


/**
 * Advice class to handle internal server errors and provide custom error response.
 */
@ControllerAdvice
public class InternalErrorAdvice {
    /**
     * Exception handler for InternalErrorException.
     *
     * @param exception The InternalErrorException instance.
     * @return A map containing the error message.
     */
    @ResponseBody
    @ExceptionHandler(InternalErrorException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, String> exceptionHandler(InternalErrorException exception){
        Map<String, String> err = new HashMap<>();
        err.put("errorMessage", exception.getMessage());
        return err;
    }
}
