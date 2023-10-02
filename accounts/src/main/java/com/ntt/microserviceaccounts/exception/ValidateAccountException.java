package com.ntt.microserviceaccounts.exception;

public class ValidateAccountException extends RuntimeException{
    public ValidateAccountException(String message){
        super(message);
    }
}
