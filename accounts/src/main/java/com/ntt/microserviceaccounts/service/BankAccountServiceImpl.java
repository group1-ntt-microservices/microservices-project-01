package com.ntt.microserviceaccounts.service;

import com.ntt.microserviceaccounts.domain.model.enity.*;
import com.ntt.microserviceaccounts.domain.repository.BankAccountRepository;
import com.ntt.microserviceaccounts.domain.service.BankAccountService;
import com.ntt.microserviceaccounts.exception.BankAccountNotFoundException;
import com.ntt.microserviceaccounts.external.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.rmi.server.UID;
import java.util.List;
import java.util.UUID;

@Service

public class BankAccountServiceImpl implements BankAccountService {

    @Autowired
    private BankAccountRepository bankAccountRepository;
    @Autowired
    private CustomerService customerService;



    public BankAccount save(BankAccount bankAccount){
        return bankAccountRepository.save(bankAccount);
    }

    @Override
    public List<BankAccount> getAll() {
        return bankAccountRepository.findAll();
    }


    @Override
    public List<BankAccount> getAllAccountsCustomer(String documentNumber) {
        return bankAccountRepository.findByDocumentNumber(documentNumber);
    }


    @Override
    public BankAccount getBankAccount(String accountNumber) {
        if (!bankAccountRepository.existsByAccountNumber(accountNumber)){
            throw new BankAccountNotFoundException(accountNumber);
        }
        return bankAccountRepository.findByAccountNumber(accountNumber);
    }
    @Override
    public boolean validateAccount(String accountNumber) {
        return bankAccountRepository.existsByAccountNumber(accountNumber);
    }

    @Override
    public BankAccount updateBankAccount(String accountNumber, BankAccount bankAccount) {
        BankAccount bankAccount1 = bankAccountRepository.findByAccountNumber(accountNumber);
        if(bankAccount1 != null){
            bankAccount1.setBalance(bankAccount.getBalance());
            bankAccount1.setCompletedTransactions(bankAccount.getCompletedTransactions());
            return bankAccountRepository.save(bankAccount1);
        }
        return null;
    }




}
