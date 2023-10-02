package com.ntt.microserviceaccounts.exception;

/**
 * Custom exception class for handling customer not found errors.
 */
public class CustomerNotFoundException extends RuntimeException{
    public CustomerNotFoundException(String documentNumber){
        super("Can't find customer with document number: "+documentNumber);
    }
}
