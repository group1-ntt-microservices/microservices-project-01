package com.ntt.microserviceaccounts.external;


import com.ntt.microserviceaccounts.domain.model.dto.BusinessCustomerDTO;
import com.ntt.microserviceaccounts.domain.model.dto.CustomerDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Feign client interface for interacting with the customers-service.
 */
@FeignClient(name = "customers-service", url = "http://localhost:8085/customerService/api/customers")
public interface CustomerService {

    /**
     * Retrieves a customer by document number.
     *
     * @param documentNumber The document number of the customer.
     * @return An Optional containing the CustomerDTO if found, empty otherwise.
     */
    @GetMapping("documentNumber/{documentNumber}")
    Optional<CustomerDTO> findByDocumentNumber(@PathVariable String documentNumber);

    /**
     * Retrieves a business customer by document number.
     *
     * @param documentNumber The document number of the business customer.
     * @return An Optional containing the BusinessCustomerDTO if found, empty otherwise.
     */
    @GetMapping("business/documentNumber/{documentNumber}")
    Optional<BusinessCustomerDTO> findByBusinessDocumentNumber(@PathVariable String documentNumber);
}