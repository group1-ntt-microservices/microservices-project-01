package com.ntt.microserviceaccounts.service;

import com.ntt.microserviceaccounts.domain.model.dto.BankAccountDTO;
import com.ntt.microserviceaccounts.domain.model.dto.CustomerDTO;
import com.ntt.microserviceaccounts.domain.model.entity.*;
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

import java.util.*;
import java.util.stream.Collectors;

/**
 * Implementation of the BankAccountService interface. Provides methods to interact with bank accounts.
 */
@Service
public class BankAccountServiceImpl implements BankAccountService {

    @Autowired
    private BankAccountRepository bankAccountRepository;
    @Autowired
    private CustomerService customerService;

    /**
     * Saves a bank account in the repository.
     *
     * @param bankAccount The bank account to be saved.
     * @return The saved bank account.
     */
    public BankAccount save(BankAccount bankAccount){
        return bankAccountRepository.save(bankAccount);
    }

    /**
     * Retrieves all bank accounts from the repository.
     *
     * @return A list of bank accounts.
     */
    @Override
    public List<BankAccount> getAll() {
        try {
            return bankAccountRepository.findAll();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    /**
     * Retrieves all bank accounts associated with a specific customer using their document number.
     *
     * @param documentNumber The document number of the customer.
     * @return A list of bank accounts associated with the customer.
     * @throws CustomerNotFoundException If the customer is not found.
     * @throws InternalErrorException If there is an internal server error.
     */
    @Override
    public List<BankAccount> getAllAccountsCustomer(String documentNumber) {
        try {
            Optional<CustomerDTO> customer = customerService.findByDocumentNumber(documentNumber);
            return bankAccountRepository.findByDocumentNumber(documentNumber);
        } catch (FeignException e) {
            if (e.status() == HttpStatus.NOT_FOUND.value()) {
                throw new CustomerNotFoundException(documentNumber);
            }
            throw new InternalErrorException("Internal server error: code 500");
        }
    }

    /**
     * Retrieves a bank account by its account number.
     *
     * @param accountNumber The account number of the bank account.
     * @return An Optional containing the bank account if found, empty otherwise.
     * @throws BankAccountNotFoundException If the bank account is not found.
     */
    @Override
    public Optional<BankAccount> getBankAccount(String accountNumber) {
        Optional<BankAccount> bankAccount = bankAccountRepository.findByAccountNumber(accountNumber);
        if (bankAccount.isEmpty()){
            throw new BankAccountNotFoundException(accountNumber);
        }
        return bankAccount;
    }

    /**
     * Updates a bank account's balance and completed transactions.
     *
     * @param accountNumber The account number of the bank account to update.
     * @param bankAccountDTO The DTO containing the updated balance and completed transactions.
     * @return The updated bank account.
     * @throws BankAccountNotFoundException If the bank account is not found.
     * @throws InternalErrorException If there is an internal server error.
     */
    @Override
    public BankAccount updateBankAccount(String accountNumber, BankAccountDTO bankAccountDTO) {
        try {
            Optional<BankAccount> account = bankAccountRepository.findByAccountNumber(accountNumber);
            return account.map(acc -> {
                acc.setBalance(bankAccountDTO.getBalance());
                acc.setCompletedTransactions(bankAccountDTO.getCompletedTransactions());
                return bankAccountRepository.save(acc);
            }).orElseThrow(() -> new BankAccountNotFoundException(accountNumber));
        } catch (Exception e) {
            throw new InternalErrorException("Internal server error: " + e.getMessage());
        }
    }

    /**
     * Deletes a bank account by its ID.
     *
     * @param id The ID of the bank account to delete.
     * @throws BankAccountNotFoundException If the bank account is not found.
     */
    @Override
    public void deleteAccount(String id) {
        if (!bankAccountRepository.existsById(id)) {
            throw new BankAccountNotFoundException(id);
        }
        bankAccountRepository.deleteById(id);
    }

    /**
     * Retrieves specific data from bank accounts such as balance, account number, completed transactions,
     * and account type for a given customer identified by document number.
     *
     * @param documentNumber The document number of the customer to retrieve bank account data for.
     * @return A list of maps, where each map contains selected fields of a bank account.
     * @throws CustomerNotFoundException If the customer with the given document number is not found.
     * @throws InternalErrorException    If there is an internal server error during the process.
     */
    @Override
    public List<Map<String, Object>> getBalancesAccounts(String documentNumber) {
        try {
            Optional<CustomerDTO> customer = customerService.findByDocumentNumber(documentNumber);
            List<BankAccount> listBankAccounts = bankAccountRepository.findByDocumentNumber(documentNumber);

            List<Map<String, Object>> listBalance =  listBankAccounts.stream().map(acc -> {
                Map<String, Object> accountData = new HashMap<>();
                accountData.put("balance", acc.getBalance());
                accountData.put("completedTransactions", acc.getCompletedTransactions());
                accountData.put("accountNumber", acc.getAccountNumber());
                accountData.put("typeAccount", acc.getTypeAccount());
                return accountData;
            }).collect(Collectors.toList());
            return listBalance;
        } catch (FeignException e) {
            if (e.status() == HttpStatus.NOT_FOUND.value()) {
                throw new CustomerNotFoundException(documentNumber);
            }
            throw new InternalErrorException("Internal server error: code 500");
        }
    }
}