package com.ntt.microserviceaccounts.exception;

/**
 * Custom exception class for representing duplicate document number errors.
 */
public class DuplicateDocumetNumberException extends RuntimeException{
    public DuplicateDocumetNumberException(String message){
        super("The document number already exists : "+message);
    }
}
