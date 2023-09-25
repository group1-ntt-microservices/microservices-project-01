package com.ntt.microservice.customers.api.controller;

import com.ntt.microservice.customers.domain.model.Customer;
import com.ntt.microservice.customers.domain.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
public class CustomerController {

    private CustomerService customerService;

    @GetMapping("/")
    public ResponseEntity<List<Customer>> findAll(){
        return ResponseEntity.ok(customerService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> findById(@PathVariable String id){
        Optional<Customer> optional = customerService.findById(id);
        return optional.map(
                ResponseEntity::ok
        ).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/documentNumber/{documentNumber}")
    public ResponseEntity<Customer> findByDocumentNumber(@PathVariable String documentNumber){
        Optional<Customer> optional = customerService.findByDocumentNumber(documentNumber);
        return optional.map(
                ResponseEntity::ok
        ).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
