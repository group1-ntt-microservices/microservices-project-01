package com.ntt.microserviceaccounts.api;


import com.ntt.microserviceaccounts.domain.model.enity.CurrentAccount;
import com.ntt.microserviceaccounts.domain.service.CurrentAccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * Controller for currents accounts.
 */
@RestController
@RequestMapping("currentaccounts")
@Api(tags  = "Current Account", description = "Everything about your current accounts")
public class CurrentAccountController {

    @Autowired
    private CurrentAccountService currentAccountService;


    /**
     * This method fetches a list of Current accounts
     * @return A response entity containing a list of Current accounts.
     */
    @ApiOperation(value = "Return all current accounts")
    @GetMapping
    public ResponseEntity<List<CurrentAccount>> fetchAll(){
        List<CurrentAccount> listCurrentAccounts = currentAccountService.getAll() ;
        if (!listCurrentAccounts.isEmpty()){
            return ResponseEntity.ok(listCurrentAccounts);
        }
        return ResponseEntity.noContent().build();
    }

    /**
     * Method to create a Current account.
     *
     * @param currentAccount The object representing the checking account to create.
     * @return A ResponseEntity containing a Current Account.
     */
    @ApiOperation(value = "Add a new Current account")
    @PostMapping("/")
    public ResponseEntity<CurrentAccount> save(@RequestBody CurrentAccount currentAccount){
        return ResponseEntity.status(HttpStatus.CREATED).body(currentAccountService.save(currentAccount));
    }

    /**
     * Method to update information for account holders and authorized signatories in a current account.
     *
     * @param accountNumber Current account number.
     * @param currentAccount The object representing the current account with the changes to be made.
     * @param typeCustomer It is the type of customer being updated (account headlines or authorizeds).
     * @return A ResponseEntity containing a Current account.
     */
    @ApiOperation(value = "Add a holder or signer to the current account")
    @PatchMapping("{accountNumber}/{type}")
    public ResponseEntity<CurrentAccount> updateCurrentAccount(
            @PathVariable String accountNumber,
            @RequestBody CurrentAccount currentAccount,
            @PathVariable("type") String typeCustomer)
    {
        CurrentAccount resp = currentAccountService.updateCurrentAccount(accountNumber, currentAccount, typeCustomer);
        return ResponseEntity.ok(resp);
    }
}
