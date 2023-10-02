package com.ntt.microservicetransactions.infraestructure.feignclient;

import com.ntt.microservicetransactions.domain.model.dto.CreditDTO;
import com.ntt.microservicetransactions.domain.model.dto.UpdatedCreditDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * This interface defines the Feign client for interacting with the Credit Service.
 */
@FeignClient(name="credit-service", url = "http://localhost:8085", path = "/creditService/api/credits")
public interface CreditClient {

    /**
     * Retrieve a credit with an id of Credit
     *
     * @param id The ID of credit
     * @return An instance of class CreditDTO.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CreditDTO> getCredit(@PathVariable("id") String id);

    /**
     * Update a credit with an ID of Credit
     *
     * @param id The ID of credit
     * @param updatedCreditDTO The object updated
     * @return An instance of class Object.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateCredit(@PathVariable("id") String id, @RequestBody UpdatedCreditDTO updatedCreditDTO);

}

