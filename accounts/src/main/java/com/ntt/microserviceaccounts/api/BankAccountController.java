package com.ntt.microserviceaccounts.api;

import com.ntt.microserviceaccounts.domain.model.enity.BankAccount;
import com.ntt.microserviceaccounts.domain.model.dto.BankAccountDTO;
import com.ntt.microserviceaccounts.domain.service.BankAccountService;
import com.ntt.microserviceaccounts.external.CustomerService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/bankaccounts")
@Api(tags  = "Bank Account", description = "accounts")

public class BankAccountController {

    @Autowired
    private BankAccountService bankAccountService;

    @Autowired
    private CustomerService customerService;


    /**
     * This method fetches a list of bank accounts from the database
     * and returns them as a response entity with a status code of 200 (OK)
     * if the operation is successful.
     * @return A response entity containing a list of bank accounts.
     */
    @GetMapping
    public ResponseEntity<List<BankAccount>> fetchAll(){
        List<BankAccount> listBankAccounts = bankAccountService.getAll();
        if (!listBankAccounts.isEmpty()){
            return ResponseEntity.ok(listBankAccounts);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(listBankAccounts);
    }

    /**
     * The method filters the accounts by user.
     *
     * @param documentNumber The customer document number.
     * @return A list of accounts.
     */
    @GetMapping("accounts")
    public ResponseEntity<List<BankAccount>> getAllAccounts(@RequestParam("document") String documentNumber){
        List<BankAccount> listBankAccounts = bankAccountService.getAllAccountsCustomer(documentNumber);
        if (!listBankAccounts.isEmpty()){
            return ResponseEntity.ok(listBankAccounts);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(listBankAccounts);
    }
    /**
     * This method fetches a bank account from the database based on the provided
     * account number and returns it.
     *
     * @param accountNumber The account number of the bank account to retrieve.
     * @return The bank account matching the provided account number or an HTTP 404 response if not found.
     */
    @GetMapping("accountNumber/{accountNumber}")
    public ResponseEntity<BankAccount> getBankAccount(@PathVariable String accountNumber){
        BankAccount account = bankAccountService.getBankAccount(accountNumber).get();
        return ResponseEntity.ok(account);
    }
    @PutMapping("{accountNumber}")
    public ResponseEntity<BankAccount> updateBankAccount(@PathVariable String accountNumber, @RequestBody BankAccountDTO bankAccountDTO){
      BankAccount account = bankAccountService.updateBankAccount(accountNumber, bankAccountDTO);
       return ResponseEntity.ok(account);
    }
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable String id) {
        bankAccountService.deleteAccount(id);
        return ResponseEntity.noContent().build();
    }
}
