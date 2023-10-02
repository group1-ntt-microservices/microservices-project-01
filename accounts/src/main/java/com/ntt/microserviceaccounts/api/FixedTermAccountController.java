package com.ntt.microserviceaccounts.api;


import com.ntt.microserviceaccounts.domain.model.enity.FixedTermAccount;
import com.ntt.microserviceaccounts.domain.model.enity.SavingAccount;
import com.ntt.microserviceaccounts.domain.service.FixedTermAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/fixedtermaccounts")
public class FixedTermAccountController {

    @Autowired
    private FixedTermAccountService fixedTermAccountService;


    @GetMapping
    public List<FixedTermAccount> fetchAll(){
        return fixedTermAccountService.getAll();
    }

    @PostMapping("{documentNumber}")
    public ResponseEntity<Map<String, Object>> save(@RequestBody FixedTermAccount fixedTermAccount, @PathVariable String documentNumber){
        Map<String, Object> resp = fixedTermAccountService.save(fixedTermAccount,documentNumber);

        boolean succes = (boolean) resp.get("succes");
        if(succes){
            return ResponseEntity.status(HttpStatus.CREATED).body(resp);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resp);
    }

}
