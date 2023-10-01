package com.ntt.microservicetransactions.infraestructure.feignclient;

import com.ntt.microservicetransactions.domain.model.dto.BankAccountDTO;
import com.ntt.microservicetransactions.domain.model.dto.CreditDTO;
import com.ntt.microservicetransactions.domain.model.dto.UpdatedCreditCardDTO;
import com.ntt.microservicetransactions.domain.model.dto.UpdatedCreditDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="credit-service", url = "http://192.168.0.25:8083", path = "/credit")
public interface CreditClient {

    @GetMapping("/{id}")
    public ResponseEntity<CreditDTO> getCredit(@PathVariable("id") String id);

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateCredit(@PathVariable("id") String id, @RequestBody UpdatedCreditDTO updatedCreditDTO);

}

