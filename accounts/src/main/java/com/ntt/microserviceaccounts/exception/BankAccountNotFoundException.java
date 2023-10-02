package com.ntt.microserviceaccounts.exception;

/**
 * Exception thrown when a bank account with a specific account number cannot be found.
 */
public class BankAccountNotFoundException extends RuntimeException{
    public BankAccountNotFoundException(String accountNumber){
        super("Can't find account with account number: "+accountNumber);
    }
}
