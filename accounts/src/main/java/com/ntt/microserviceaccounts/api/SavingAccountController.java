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


    @PostMapping
    public ResponseEntity<Map<String, Object>> save(@RequestBody SavingAccount savingAccount){
        Map<String, Object> resp = savingAccountService.save(savingAccount, savingAccount.getDocumentNumber());
        Integer success = (Integer) resp.get("success");

        HttpStatus httpStatus = (success == 1) ? HttpStatus.CREATED :
                                (success == 0) ? HttpStatus.BAD_REQUEST :
                                 HttpStatus.INTERNAL_SERVER_ERROR;
        return ResponseEntity.status(httpStatus).body(resp);
    }



}
