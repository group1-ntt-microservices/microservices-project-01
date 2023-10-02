package com.ntt.microservicetransactions.domain.model.exception;

/**
 * Represents the exception insufficient parameter
 */
public class InsufficientParameterException extends RuntimeException{
    public InsufficientParameterException(String message){
        super(message);
    }
}
