package com.ntt.microserviceaccounts.exception;

public class BankAccountExistsException extends RuntimeException{
    public BankAccountExistsException(String accountNumber){
        super("The account number already exists :"+accountNumber);
    }
}
