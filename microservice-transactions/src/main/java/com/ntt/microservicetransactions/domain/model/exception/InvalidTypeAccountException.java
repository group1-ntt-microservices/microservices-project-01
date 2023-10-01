package com.ntt.microservicetransactions.domain.model.exception;

public class InvalidTypeAccountException extends RuntimeException{
    public InvalidTypeAccountException(String message){
        super(message);
    }
}
