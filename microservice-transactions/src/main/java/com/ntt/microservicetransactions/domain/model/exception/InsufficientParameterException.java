package com.ntt.microservicetransactions.domain.model.exception;

public class InsufficientParameterException extends RuntimeException{
    public InsufficientParameterException(String message){
        super(message);
    }
}
