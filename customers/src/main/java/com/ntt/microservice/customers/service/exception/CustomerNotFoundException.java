package com.ntt.microservice.customers.service.exception;

/**
 * Exception thrown when a customer is not found.
 */
public class CustomerNotFoundException extends RuntimeException{
    public CustomerNotFoundException(){
        super();
    }
}
