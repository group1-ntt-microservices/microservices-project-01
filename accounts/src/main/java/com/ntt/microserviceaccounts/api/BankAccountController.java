package com.ntt.microserviceaccounts.api;

import com.ntt.microserviceaccounts.domain.model.enity.BankAccount;
import com.ntt.microserviceaccounts.domain.model.enity.BankAccountDTO;
import com.ntt.microserviceaccounts.domain.service.BankAccountService;
import com.ntt.microserviceaccounts.external.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/bankaccounts")
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
            return ResponseEntity.ok(bankAccountService.getAll());
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
            return ResponseEntity.ok(bankAccountService.getAllAccountsCustomer(documentNumber));
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
    @GetMapping("accountnumber/{accountNumber}")
    public ResponseEntity<BankAccount> getBankAccount(@PathVariable String accountNumber){
        Optional<BankAccount> account = bankAccountService.getBankAccount(accountNumber);
        return account.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    @PutMapping("{accountNumber}")
    public ResponseEntity<Object> updateBankAccount(@PathVariable String accountNumber, @RequestBody BankAccountDTO bankAccountDTO){
        Optional<BankAccount> account  = bankAccountService.getBankAccount(accountNumber);
        if (account.isPresent()) {
            account.get().setBalance(bankAccountDTO.getBalance());
            account.get().setCompletedTransactions(bankAccountDTO.getCompletedTransactions());
            bankAccountService.save(account.get());
            return ResponseEntity.ok().body(account);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("");
    }


}
