package com.ntt.microserviceaccounts.external;


import com.ntt.microserviceaccounts.domain.model.enity.Customer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@FeignClient(name = "customer-api", url = "http://localhost:8081/")
public interface CustomerService {


    @GetMapping("documentNumber/{documentNumber}")
    Optional<Customer> findByDocumentNumber(@PathVariable String documentNumber);



    /*
    @PatchMapping("{documentNumber}")
    Customer updateCustomer(@PathVariable String documentNumber, @RequestBody Customer customer);
    */

}
