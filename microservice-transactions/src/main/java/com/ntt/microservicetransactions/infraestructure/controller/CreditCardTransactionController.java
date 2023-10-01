package com.ntt.microservicetransactions.infraestructure.controller;

import com.ntt.microservicetransactions.domain.model.dto.CreditCardTransactionDTO;
import com.ntt.microservicetransactions.domain.model.dto.CreditTransactionDTO;
import com.ntt.microservicetransactions.domain.service.CreditCardTransactionService;
import com.ntt.microservicetransactions.domain.service.CreditTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/creditCards/transactions")
public class CreditCardTransactionController {

    @Autowired
    private CreditCardTransactionService creditCardTransactionService;

    @PostMapping
    public ResponseEntity<CreditCardTransactionDTO> createCreditCardTransaction(@RequestBody CreditCardTransactionDTO creditCardTransactionDTO){
        return new ResponseEntity<>(creditCardTransactionService.createCreditCardTransaction(creditCardTransactionDTO), HttpStatus.OK);
    }

    @GetMapping
    public List<CreditCardTransactionDTO> getFilteredCreditCardTransactions(@RequestParam(name="creditCardId",required = false) String creditCardId,
                                                                            @RequestParam(name="customerDocumentNumber",required = false) String customerDocumentNumber){
        return creditCardTransactionService.getFilteredCreditCardTransactions(creditCardId, customerDocumentNumber);
    }

}
