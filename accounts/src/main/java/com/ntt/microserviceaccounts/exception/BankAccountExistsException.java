package com.ntt.microserviceaccounts.exception;

/**
 * Custom exception class for indicating that a bank account with a specific account number already exists.
 */
public class BankAccountExistsException extends RuntimeException{
    public BankAccountExistsException(String accountNumber){
        super("The account number already exists :"+accountNumber);
    }
}
