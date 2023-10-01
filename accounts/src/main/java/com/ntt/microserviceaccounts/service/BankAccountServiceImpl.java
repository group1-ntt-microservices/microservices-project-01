package com.ntt.microserviceaccounts.service;

import com.ntt.microserviceaccounts.domain.model.enity.*;
import com.ntt.microserviceaccounts.domain.repository.BankAccountRepository;
import com.ntt.microserviceaccounts.domain.service.BankAccountService;
import com.ntt.microserviceaccounts.exception.BankAccountNotFoundException;
import com.ntt.microserviceaccounts.external.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.rmi.server.UID;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
        try {
            return bankAccountRepository.findAll();
        }catch (Exception e){
            return new ArrayList<>();
        }
    }


    @Override
    public List<BankAccount> getAllAccountsCustomer(String documentNumber) {
        try {
            Optional<Customer> customer = customerService.findByDocumentNumber(documentNumber);

            return bankAccountRepository.findByDocumentNumber(documentNumber);
        }catch (Exception e){
            return new ArrayList<>();
        }
    }


    @Override
    public Optional<BankAccount> getBankAccount(String accountNumber) {
        return bankAccountRepository.findByAccountNumber(accountNumber);
    }
    @Override
    public boolean validateAccount(String accountNumber) {
        return bankAccountRepository.existsByAccountNumber(accountNumber);
    }


    @Override
    public BankAccount updateBankAccount(String accountNumber, BankAccount bankAccount) {
        Optional<BankAccount> bankAccount1 = bankAccountRepository.findByAccountNumber(accountNumber);
        if(bankAccount1.isPresent()){
            bankAccount1.get().setBalance(bankAccount.getBalance());
            bankAccount1.get().setCompletedTransactions(bankAccount.getCompletedTransactions());
            return bankAccountRepository.save(bankAccount1.get());
        }
        return null;
    }




}
