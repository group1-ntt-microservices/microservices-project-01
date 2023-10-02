package com.ntt.microserviceaccounts.exception;

/**
 * Custom exception class for internal server errors.
 */
public class InternalErrorException extends RuntimeException{
    public InternalErrorException(String message){
        super("Internal Error Server Error : "+message);
    }
}
