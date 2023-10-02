package com.ntt.microservicetransactions.infraestructure.controller;

import com.ntt.microservicetransactions.domain.model.dto.CreditTransactionDTO;
import com.ntt.microservicetransactions.domain.model.exception.InsufficientBalanceException;
import com.ntt.microservicetransactions.domain.service.CreditTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/credits/transactions")
public class CreditTransactionController {

    @Autowired
    private CreditTransactionService creditTransactionService;

    @PostMapping
    public ResponseEntity<CreditTransactionDTO> createCreditTransaction(@RequestBody CreditTransactionDTO creditTransactionDTO) {
        if(creditTransactionDTO.getAmount()<0){
            throw new InsufficientBalanceException("El monto no puede ser negativo");
        }
        return new ResponseEntity<>(creditTransactionService.createCreditTransaction(creditTransactionDTO), HttpStatus.OK);
    }

    @GetMapping
    public List<CreditTransactionDTO> getFilteredCreditTransactions(@RequestParam(name = "creditId", required = false) String creditId,
                                                                    @RequestParam(name = "customerDocumentNumber", required = false) String customerDocumentNumber) {
        return creditTransactionService.getFilteredCreditTransactions(creditId, customerDocumentNumber);
    }

}
