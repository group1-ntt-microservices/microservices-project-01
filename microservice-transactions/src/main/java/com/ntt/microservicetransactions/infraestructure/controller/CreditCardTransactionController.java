package com.ntt.microservicetransactions.infraestructure.controller;

import com.ntt.microservicetransactions.domain.model.dto.CreditCardTransactionDTO;
import com.ntt.microservicetransactions.domain.model.dto.CreditTransactionDTO;
import com.ntt.microservicetransactions.domain.model.exception.InsufficientBalanceException;
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


    /**
     * Adds a new credit card transaction
     *
     * @param creditCardTransactionDTO The data transfer object of credit card transaction
     * @return An instance of class CreditCardTransactionDTO.
     */
    @PostMapping
    public ResponseEntity<CreditCardTransactionDTO> createCreditCardTransaction(@RequestBody CreditCardTransactionDTO creditCardTransactionDTO){
        if(creditCardTransactionDTO.getAmount()<0){
            throw new InsufficientBalanceException("El monto no puede ser negativo");
        }
        return new ResponseEntity<>(creditCardTransactionService.createCreditCardTransaction(creditCardTransactionDTO), HttpStatus.OK);
    }


    /**
     * Retrieves a list of credit card transactions
     *
     * @param creditCardId The ID of credit card
     * @param customerDocumentNumber The number of document customer
     * @return A list of class CreditCardTransactionDTO.
     */
    @GetMapping
    public List<CreditCardTransactionDTO> getFilteredCreditCardTransactions(@RequestParam(name="creditCardId",required = false) String creditCardId,
                                                                            @RequestParam(name="customerDocumentNumber",required = false) String customerDocumentNumber){
        return creditCardTransactionService.getFilteredCreditCardTransactions(creditCardId, customerDocumentNumber);
    }

}
