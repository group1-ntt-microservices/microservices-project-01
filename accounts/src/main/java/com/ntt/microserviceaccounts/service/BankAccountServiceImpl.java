package com.ntt.microserviceaccounts.service;

import com.ntt.microserviceaccounts.domain.model.dto.BankAccountDTO;
import com.ntt.microserviceaccounts.domain.model.dto.CustomerDTO;
import com.ntt.microserviceaccounts.domain.model.enity.*;
import com.ntt.microserviceaccounts.domain.repository.BankAccountRepository;
import com.ntt.microserviceaccounts.domain.service.BankAccountService;
import com.ntt.microserviceaccounts.exception.BankAccountNotFoundException;
import com.ntt.microserviceaccounts.exception.CustomerNotFoundException;
import com.ntt.microserviceaccounts.exception.InternalErrorException;
import com.ntt.microserviceaccounts.external.CustomerService;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
            Optional<CustomerDTO> customer = customerService.findByDocumentNumber(documentNumber);
            return bankAccountRepository.findByDocumentNumber(documentNumber);
        } catch (FeignException e) {
            if (e.status() == HttpStatus.NOT_FOUND.value()) {
                throw new CustomerNotFoundException(documentNumber);
            }
            throw new InternalErrorException("code 500");
        }
    }


    @Override
    public Optional<BankAccount> getBankAccount(String accountNumber) {
        Optional<BankAccount> bankAccount = bankAccountRepository.findByAccountNumber(accountNumber);
        if (bankAccount.isEmpty()){
            throw new BankAccountNotFoundException(accountNumber);
        }
        return bankAccount;
    }
    @Override
    public boolean validateAccount(String accountNumber) {
        return bankAccountRepository.existsByAccountNumber(accountNumber);
    }


    @Override
    public BankAccount updateBankAccount(String accountNumber, BankAccountDTO bankAccountDTO) {
        try {
            Optional<BankAccount> account = bankAccountRepository.findByAccountNumber(accountNumber);
            return account.map(acc ->{
                    acc.setBalance(bankAccountDTO.getBalance());
                    acc.setCompletedTransactions(bankAccountDTO.getCompletedTransactions());
                    return bankAccountRepository.save(acc);
            } ).orElseThrow(() -> new BankAccountNotFoundException(accountNumber));
        }catch (Exception e){
            throw new InternalErrorException(e.getMessage());
        }
    }

    @Override
    public void deleteAccount(String id) {
       if(!bankAccountRepository.existsById(id)){
           throw new BankAccountNotFoundException(id);
       }
        bankAccountRepository.deleteById(id);
    }


}
