package com.ntt.microservicetransactions.domain.model.exception;

/**
 * Represents the exception limit transaction exceeded
 */
public class LimitTransactionsExceededException extends RuntimeException{
    public LimitTransactionsExceededException(String message){
        super(message);
    }
}
