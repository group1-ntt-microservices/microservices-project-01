package com.ntt.microserviceaccounts.exception;

public class DuplicateDocumetNumberException extends RuntimeException{
    public DuplicateDocumetNumberException(String message){
        super("The document number already exists : "+message);
    }
}
