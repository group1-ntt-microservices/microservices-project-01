package com.ntt.microservicetransactions.domain.model.exception;

/**
 * Represents the exception method call failure
 */
public class MethodCallFailureException extends RuntimeException{
    public MethodCallFailureException(String message){
        super(message);
    }
}
