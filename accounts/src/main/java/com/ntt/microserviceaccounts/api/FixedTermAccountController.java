package com.ntt.microserviceaccounts.api;


import com.ntt.microserviceaccounts.domain.model.enity.FixedTermAccount;
import com.ntt.microserviceaccounts.domain.model.enity.SavingAccount;
import com.ntt.microserviceaccounts.domain.service.FixedTermAccountService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/fixedtermaccounts")
@Api(tags  = "Fixed Term Account", description = "accounts")
public class FixedTermAccountController {

    @Autowired
    private FixedTermAccountService fixedTermAccountService;


    @GetMapping
    public ResponseEntity<List<FixedTermAccount>> fetchAll(){
        List<FixedTermAccount> listFixedAccounts = fixedTermAccountService.getAll();
        if (!listFixedAccounts.isEmpty()){
            return ResponseEntity.ok(listFixedAccounts);
        }
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<FixedTermAccount> save(@RequestBody FixedTermAccount fixedTermAccount){
        return ResponseEntity.status(HttpStatus.CREATED).body(fixedTermAccountService.save(fixedTermAccount));
    }

}
