package com.ntt.microservicetransactions.infraestructure.feignclient;

import com.ntt.microservicetransactions.domain.model.dto.BankAccountDTO;
import com.ntt.microservicetransactions.domain.model.dto.CreditCardDTO;
import com.ntt.microservicetransactions.domain.model.dto.UpdatedBankAccountDTO;
import com.ntt.microservicetransactions.domain.model.dto.UpdatedCreditCardDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="credit-card-service", url = "http://localhost:8085", path = "/creditService/api/credits/creditCards")
public interface CreditCardClient {

    @GetMapping("/{id}")
    public ResponseEntity<CreditCardDTO> getCreditCard(@PathVariable("id") String id);

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateCreditCard(@PathVariable("id") String id, @RequestBody UpdatedCreditCardDTO updatedCreditCardDTO);

}

