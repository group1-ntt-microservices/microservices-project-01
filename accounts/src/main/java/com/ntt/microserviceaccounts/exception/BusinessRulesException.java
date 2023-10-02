package com.ntt.microserviceaccounts.exception;

/**
 * Custom exception class for handling business rule violations in the application.
 */
public class BusinessRulesException extends RuntimeException{
    public BusinessRulesException(String message){
        super(message);
    }
}
