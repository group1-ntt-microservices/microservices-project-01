package com.ntt.microserviceaccounts.exception;

public class BankAccountNotFoundException extends RuntimeException{
    public BankAccountNotFoundException(String accountNumber){
        super("Can't find account with account number: "+accountNumber);
    }
}
