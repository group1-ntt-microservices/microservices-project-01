package com.ntt.microservicetransactions.domain.model.exception;

/**
 * Represents the exception insufficient balance
 */
public class InsufficientBalanceException extends RuntimeException{
    public InsufficientBalanceException(String message){
        super(message);
    }
}
