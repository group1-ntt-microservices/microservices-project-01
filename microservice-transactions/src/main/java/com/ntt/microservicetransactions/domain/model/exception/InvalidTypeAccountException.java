package com.ntt.microservicetransactions.domain.model.exception;

/**
 * Represents the exception invalid type account
 */
public class InvalidTypeAccountException extends RuntimeException{
    public InvalidTypeAccountException(String message){
        super(message);
    }
}
