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

    /**
     * Adds a new credit transaction
     *
     * @param creditTransactionDTO The data transfer object of credit transaction
     * @return An instance of class CreditTransactionDTO.
     */
    @PostMapping
    public ResponseEntity<CreditTransactionDTO> createCreditTransaction(@RequestBody CreditTransactionDTO creditTransactionDTO) {
        if(creditTransactionDTO.getAmount()<0){
            throw new InsufficientBalanceException("El monto no puede ser negativo");
        }
        return new ResponseEntity<>(creditTransactionService.createCreditTransaction(creditTransactionDTO), HttpStatus.OK);
    }

    /**
     * Retrieves a list of credit transactions
     *
     * @param creditId The ID of credit
     * @param customerDocumentNumber The number of document customer
     * @return A list of class CreditTransactionDTO.
     */
    @GetMapping
    public List<CreditTransactionDTO> getFilteredCreditTransactions(@RequestParam(name = "creditId", required = false) String creditId,
                                                                    @RequestParam(name = "customerDocumentNumber", required = false) String customerDocumentNumber) {
        return creditTransactionService.getFilteredCreditTransactions(creditId, customerDocumentNumber);
    }

}
