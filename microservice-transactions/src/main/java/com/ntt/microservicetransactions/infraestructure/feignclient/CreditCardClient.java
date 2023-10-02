package com.ntt.microservicetransactions.infraestructure.feignclient;

import com.ntt.microservicetransactions.domain.model.dto.CreditCardDTO;
import com.ntt.microservicetransactions.domain.model.dto.UpdatedCreditCardDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * This interface defines the Feign client for interacting with the Credit Card Service.
 */
@FeignClient(name="credit-card-service", url = "http://localhost:8085", path = "/creditService/api/credits/creditCards")
public interface CreditCardClient {

    /**
     * Retrieve a credit card with an id of Credit Card
     *
     * @param id The ID of credit card
     * @return An instance of class CreditCardDTO.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CreditCardDTO> getCreditCard(@PathVariable("id") String id);

    /**
     * Update a credit card with an ID of Credit Card
     *
     * @param id The ID of credit card
     * @param updatedCreditCardDTO The object updated
     * @return An instance of class Object.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateCreditCard(@PathVariable("id") String id, @RequestBody UpdatedCreditCardDTO updatedCreditCardDTO);

}

