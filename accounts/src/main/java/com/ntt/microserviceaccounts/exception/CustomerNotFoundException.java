package com.ntt.microserviceaccounts.exception;

public class CustomerNotFoundException extends RuntimeException{
    public CustomerNotFoundException(String documentNumber){
        super("Can't find customer with document number: "+documentNumber);
    }
}
