package com.ntt.microservicetransactions.infraestructure.controller;

import com.ntt.microservicetransactions.domain.model.dto.BankAccountTransactionDTO;
import com.ntt.microservicetransactions.domain.model.exception.InsufficientBalanceException;
import com.ntt.microservicetransactions.domain.service.BankAccountTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/bankAccounts/transactions")
public class BankAccountTransactionController {
    @Autowired
    private BankAccountTransactionService bankAccountTransactionService;

    @PostMapping
    public ResponseEntity<BankAccountTransactionDTO> createBankAccountTransaction(@RequestBody BankAccountTransactionDTO bankAccountTransactionDTO){
        if(bankAccountTransactionDTO.getAmount()<0){
            throw new InsufficientBalanceException("El monto no puede ser negativo");
        }
        return new ResponseEntity<>(bankAccountTransactionService.createBankAccountTransaction(bankAccountTransactionDTO), HttpStatus.OK);
    }

    @GetMapping
    public List<BankAccountTransactionDTO> getFilteredBankAccountTransactions(@RequestParam(name = "bankAccountNumber",required = false) String bankAccountNumber,
                                                                              @RequestParam(name = "customerDocumentNumber",required = false) String customerDocumentNumber){
        return bankAccountTransactionService.getFilteredBankAccountTransactions(bankAccountNumber, customerDocumentNumber);
    }
}
