package com.ntt.microservicetransactions.infraestructure.feignclient;

import com.ntt.microservicetransactions.domain.model.dto.BankAccountDTO;
import com.ntt.microservicetransactions.domain.model.dto.UpdatedBankAccountDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
/**
 * This interface defines the Feign client for interacting with the Bank Account Service.
 */

@FeignClient(name="bank-account-service", url = "http://localhost:8085", path = "/accountService/accounts/api/v1/bankaccounts")

public interface BankAccountClient {

    /**
     * Retrieve a bank account with an account number
     *
     * @param accountNumber The number of bank account
     * @return An instance of class BankAccountDTO.
     */
    @GetMapping("/accountNumber/{accountNumber}")
    public ResponseEntity<BankAccountDTO> getBankAccount(@PathVariable("accountNumber") String accountNumber);

    /**
     * Update a bank account with an account number
     *
     * @param accountNumber The number of bank account
     * @return An instance of class BankAccountDTO.
     */
    @PutMapping("/{accountNumber}")
    public ResponseEntity<BankAccountDTO> updateBankAccount(@PathVariable("accountNumber") String accountNumber, @RequestBody UpdatedBankAccountDTO updatedBankAccountDTO);
}
