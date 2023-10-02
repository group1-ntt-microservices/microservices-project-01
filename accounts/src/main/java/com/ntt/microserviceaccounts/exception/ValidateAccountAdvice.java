package com.ntt.microserviceaccounts.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;
/**
 * Advice class to handle exceptions related to invalid account validation.
 */
@ControllerAdvice
public class ValidateAccountAdvice {
    /**
     * Exception handler method for ValidateAccountException.
     *
     * @param exception The ValidateAccountException to be handled.
     * @return A map containing the error message.
     */
    @ResponseBody
    @ExceptionHandler(ValidateAccountException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> exceptionHandler(ValidateAccountException exception){
        Map<String, String> err = new HashMap<>();
        err.put("errorMessage", exception.getMessage());
        return err;
    }
}
