package com.ntt.microserviceaccounts.api;


import com.ntt.microserviceaccounts.domain.model.enity.SavingAccount;
import com.ntt.microserviceaccounts.domain.service.SavingAccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;



/**
 * Controller for Current accounts.
 */
@RestController
@RequestMapping("savingaccounts")
@Api(tags  = "Saving Accounts", description = "Everything about your Saving accounts")
public class SavingAccountController {

    @Autowired
    private SavingAccountService savingAccountService;


    /**
     * This method is used to retrieve all Saving accounts.
     *
     * @return A ResponseEntity containing a list of SavingAccount objects in the response body
     *         with HTTP status 200 (OK) if there are accounts, or HTTP status 204 (NO CONTENT)
     *         if there are no accounts.
     */
    @ApiOperation(value = "Return all Saving accounts")
    @GetMapping("/")
    public ResponseEntity<List<SavingAccount>> fecthAll(){
        List<SavingAccount> SavingAccounts = savingAccountService.getAll();
        if (!SavingAccounts.isEmpty()){
            return ResponseEntity.ok(SavingAccounts);
        }
        return ResponseEntity.noContent().build();
    }

    /**
     * This method is used to add a new Saving account.
     *
     * @param savingAccount The SavingAccount object representing the account to be created.
     * @return A ResponseEntity containing the created SavingAccount object in the response body
     *         with HTTP status 201 (CREATED).
     */
    @ApiOperation(value = "Add a new Saving account")
    @PostMapping("/")
    public ResponseEntity<SavingAccount> save(@RequestBody SavingAccount savingAccount){
        return ResponseEntity.status(HttpStatus.CREATED).body(savingAccountService.save(savingAccount));
    }



}
