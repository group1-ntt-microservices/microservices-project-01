package com.ntt.microserviceaccounts.api;


import com.ntt.microserviceaccounts.domain.model.enity.CurrentAccount;
import com.ntt.microserviceaccounts.domain.model.enity.SavingAccount;
import com.ntt.microserviceaccounts.domain.service.SavingAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/savingaccounts")
public class SavingAccountController {

    @Autowired
    private SavingAccountService savingAccountService;


    @GetMapping
    public List<SavingAccount> fecthAll(){
        return savingAccountService.getAll();
    }


    @PostMapping("{documentNumber}")
    public ResponseEntity<Map<String, Object>> save(@RequestBody SavingAccount savingAccount, @PathVariable String documentNumber){
        Map<String, Object> resp = savingAccountService.save(savingAccount,documentNumber);
        boolean succes = (boolean) resp.get("succes");
        if(succes){
            return ResponseEntity.status(HttpStatus.CREATED).body(resp);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resp);
    }



}
