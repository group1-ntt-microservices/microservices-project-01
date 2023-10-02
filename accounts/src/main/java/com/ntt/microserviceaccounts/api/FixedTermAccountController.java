package com.ntt.microserviceaccounts.api;


import com.ntt.microserviceaccounts.domain.model.entity.FixedTermAccount;
import com.ntt.microserviceaccounts.domain.service.FixedTermAccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * Controller for Fixed Term accounts.
 */
@RestController
@RequestMapping("fixedtermaccounts")
@Api(tags  = "Fixed Term Account", description = "Everything about your Fixed Term accounts")
public class FixedTermAccountController {

    @Autowired
    private FixedTermAccountService fixedTermAccountService;

    /**
     * This method fetches a list of Fixed Term accounts
     * @return A response entity containing a list of Fixed Term accounts.
     */
    @ApiOperation(value = "Return all Fixed Term accounts")
    @GetMapping("/")
    public ResponseEntity<List<FixedTermAccount>> fetchAll(){
        List<FixedTermAccount> listFixedAccounts = fixedTermAccountService.getAll();
        if (!listFixedAccounts.isEmpty()){
            return ResponseEntity.ok(listFixedAccounts);
        }
        return ResponseEntity.noContent().build();
    }

    /**
     * This method is used to create a Fixed Term account.
     *
     * @param fixedTermAccount The object representing the fixed term account to be created.
     * @return A ResponseEntity containing the created FixedTermAccount and HTTP status 201 (CREATED).
     */
    @ApiOperation(value = "Add a new Fixed Term account")
    @PostMapping("/")
    public ResponseEntity<FixedTermAccount> save(@RequestBody FixedTermAccount fixedTermAccount){
        return ResponseEntity.status(HttpStatus.CREATED).body(fixedTermAccountService.save(fixedTermAccount));
    }

}
