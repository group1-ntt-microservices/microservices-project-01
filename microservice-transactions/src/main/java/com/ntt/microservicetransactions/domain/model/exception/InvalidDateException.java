package com.ntt.microservicetransactions.domain.model.exception;

/**
 * Represents the exception invalid date
 */
public class InvalidDateException extends RuntimeException{
    public InvalidDateException(String message){
        super(message);
    }
}
