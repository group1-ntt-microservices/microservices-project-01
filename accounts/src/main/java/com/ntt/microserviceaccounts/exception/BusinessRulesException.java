package com.ntt.microserviceaccounts.exception;

public class BusinessRulesException extends RuntimeException{
    public BusinessRulesException(String message){
        super(message);
    }
}
