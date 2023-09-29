package com.ntt.microserviceaccounts.api;


import com.ntt.microserviceaccounts.domain.model.enity.CurrentAccount;
import com.ntt.microserviceaccounts.domain.service.CurrentAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/currentaccounts")
public class CurrentAccountController {

    @Autowired
    private CurrentAccountService currentAccountService;

    @GetMapping
    public ResponseEntity<List<CurrentAccount>> fetchAll(){
        List<CurrentAccount> listCurrentAccounts = currentAccountService.getAll() ;

        if (!listCurrentAccounts.isEmpty()){
            return ResponseEntity.ok(listCurrentAccounts);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * Method to create a checking account.
     *
     * @param currentAccount The object representing the checking account to create.
     * @param documentNumber The customer's document number.
     * @return A ResponseEntity containing a Map<String, Object> with information about the operation result.
     */
    @PostMapping("{documentNumber}")
    public ResponseEntity<Map<String, Object>> save(@RequestBody CurrentAccount currentAccount, @PathVariable String documentNumber){
        Map<String, Object> resp = currentAccountService.save(currentAccount, documentNumber);

        boolean succes = (boolean) resp.get("succes");

        if(succes){
            return ResponseEntity.status(HttpStatus.CREATED).body(resp);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resp);
    }

    /**
     * Method to update information for account holders and authorized signatories in a current account.
     *
     * @param accountNumber Current account number.
     * @param currentAccount The object representing the current account with the changes to be made.
     * @param typeCustomer It is the type of customer being updated (account headlines or authorizeds).
     * @return A ResponseEntity containing a Map<String, Object> with information about the operation result.
     */
    @PatchMapping("{accountNumber}/{type}")
    public ResponseEntity<Map<String, Object>> updateCurrentAccount(
            @PathVariable String accountNumber,
            @RequestBody CurrentAccount currentAccount,
            @PathVariable("type") String typeCustomer)
    {
        Map<String, Object> resp = currentAccountService.updateCurrentAccount(accountNumber, currentAccount, typeCustomer);

        boolean succes = (boolean) resp.get("succes");
        if (succes){
            return ResponseEntity.status(HttpStatus.OK).body(resp);
        }
        currentAccountService.updateCurrentAccount(accountNumber, currentAccount, typeCustomer);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resp);
    }
}
