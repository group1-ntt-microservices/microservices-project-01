package com.ntt.microserviceaccounts.external;


import com.ntt.microserviceaccounts.domain.model.dto.BusinessCustomerDTO;
import com.ntt.microserviceaccounts.domain.model.dto.CustomerDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@FeignClient(name = "customer-api", url = "http://localhost:8081/api/customers")
public interface CustomerService {

    @GetMapping("documentNumber/{documentNumber}")
    Optional<CustomerDTO> findByDocumentNumber(@PathVariable String documentNumber);

    @GetMapping("business/documentNumber/{documentNumber}")
    Optional<BusinessCustomerDTO> findByBusinessDocumentNumber(@PathVariable String documentNumber);
}
