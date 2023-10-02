package com.ntt.microserviceaccounts.api;

import com.ntt.microserviceaccounts.domain.model.entity.BankAccount;
import com.ntt.microserviceaccounts.domain.model.dto.BankAccountDTO;
import com.ntt.microserviceaccounts.domain.service.BankAccountService;
import com.ntt.microserviceaccounts.external.CustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for bank accounts.
 */
@RestController
@RequestMapping("bankaccounts")
@Api(tags  = "Bank Account", description = "Everything about your bank accounts")

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

    @ApiOperation(value = "Return all bank accounts")
    @GetMapping("/")
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
    @ApiOperation(value = "Return all bank accounts of a customer")
    @GetMapping("/accounts")
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

    @ApiOperation(value = "Return a bank account by account number")
    @GetMapping("accountNumber/{accountNumber}")
    public ResponseEntity<BankAccount> getBankAccount(@PathVariable String accountNumber){
        BankAccount account = bankAccountService.getBankAccount(accountNumber).get();
        return ResponseEntity.ok(account);
    }

    /**
     * This endpoint retrieves specific data from bank accounts such as balance, account number,
     * document number, and the number of transactions for a given customer identified by document number.
     *
     * @param documentNumber The document number of the customer to retrieve bank account data for.
     * @return A ResponseEntity containing a list of maps, each map representing a bank account's selected fields.
     */
    @ApiOperation(value = "Returns all balances of a client's accounts")
    @GetMapping("balances/{documentNumber}")
    public ResponseEntity<Object> getBalancesAccount(@PathVariable String documentNumber){
          return ResponseEntity.ok(bankAccountService.getBalancesAccounts(documentNumber));
    }


    /**
     * This method updates the balance and the number of transactions.
     * @param accountNumber The account number of the bank account to retrieve.
     * @param bankAccountDTO Object that updates our bank account
     * @return ResponseEntity with the updated bank account and an appropriate HTTP status code.
     */
    @ApiOperation(value ="Update bank account balance")
    @PutMapping("{accountNumber}")
    public ResponseEntity<BankAccount> updateBankAccount(@PathVariable String accountNumber,@RequestBody BankAccountDTO bankAccountDTO){
        BankAccount account = bankAccountService.updateBankAccount(accountNumber, bankAccountDTO);
        return ResponseEntity.ok(account);
    }
    /**
     * This method deletes a bank account by its unique identifier.
     * @param id The unique identifier of the bank account to be deleted.
     * @return ResponseEntity with no content and an appropriate HTTP status code upon successful deletion.
     */
    @ApiOperation(value ="Delete an account by id")
    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteAccount(@PathVariable String id) {
        bankAccountService.deleteAccount(id);
        return ResponseEntity.noContent().build();
    }
}
