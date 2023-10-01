package com.ntt.microservicetransactions.domain.model.exception;

public class InvalidDateException extends RuntimeException{
    public InvalidDateException(String message){
        super(message);
    }
}
