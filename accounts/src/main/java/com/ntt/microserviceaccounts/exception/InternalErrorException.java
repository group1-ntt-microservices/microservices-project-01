package com.ntt.microserviceaccounts.exception;

public class InternalErrorException extends RuntimeException{
    public InternalErrorException(String message){
        super("Internal Error Server Error : "+message);
    }
}
