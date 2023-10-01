package com.ntt.microservicetransactions.domain.model.exception;

public class LimitTransactionsExceededException extends RuntimeException{
    public LimitTransactionsExceededException(String message){
        super(message);
    }
}
