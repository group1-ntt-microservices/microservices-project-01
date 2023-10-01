package com.ntt.microservicetransactions.infraestructure.feignclient;

import com.ntt.microservicetransactions.domain.model.dto.BankAccountDTO;
import com.ntt.microservicetransactions.domain.model.dto.UpdatedBankAccountDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name="bank-account-service", url = "http://192.168.0.25:8080", path = "/api/v1/bankaccounts")
public interface BankAccountClient {

    @GetMapping("/accountnumber/{accountNumber}")
    public ResponseEntity<BankAccountDTO> getBankAccount(@PathVariable("accountNumber") String accountNumber);

    @PutMapping("/{accountNumber}")
    public ResponseEntity<BankAccountDTO> updateBankAccount(@PathVariable("accountNumber") String accountNumber, @RequestBody UpdatedBankAccountDTO updatedBankAccountDTO);
}
