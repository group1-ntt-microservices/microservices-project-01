package com.ntt.microserviceaccounts.api;


import com.ntt.microserviceaccounts.domain.model.enity.BankAccount;
import com.ntt.microserviceaccounts.domain.model.enity.CurrentAccount;
import com.ntt.microserviceaccounts.domain.model.enity.SavingAccount;
import com.ntt.microserviceaccounts.domain.service.SavingAccountService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/savingaccounts")
@Api(tags  = "Saving Accounts", description = "accounts")
public class SavingAccountController {

    @Autowired
    private SavingAccountService savingAccountService;


    @GetMapping
    public ResponseEntity<List<SavingAccount>> fecthAll(){
        List<SavingAccount> SavingAccounts = savingAccountService.getAll();
        if (!SavingAccounts.isEmpty()){
            return ResponseEntity.ok(SavingAccounts);
        }
        return ResponseEntity.noContent().build();
    }


    @PostMapping
    public ResponseEntity<SavingAccount> save(@RequestBody SavingAccount savingAccount){
        return ResponseEntity.status(HttpStatus.CREATED).body(savingAccountService.save(savingAccount));
    }



}
