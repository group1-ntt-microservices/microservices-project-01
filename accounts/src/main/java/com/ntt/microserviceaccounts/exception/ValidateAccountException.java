package com.ntt.microserviceaccounts.exception;


/**
 * Custom exception class for handling validation errors related to account validation.
 */
public class ValidateAccountException extends RuntimeException{
    public ValidateAccountException(String message){
        super(message);
    }
}
