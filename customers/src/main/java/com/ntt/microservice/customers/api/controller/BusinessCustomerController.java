package com.ntt.microservice.customers.api.controller;

import com.ntt.microservice.customers.domain.model.BusinessCustomer;
import com.ntt.microservice.customers.domain.service.BusinessCustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/business")
public class BusinessCustomerController {
    private BusinessCustomerService businessCustomerService;

    @GetMapping("/")
    public ResponseEntity<List<BusinessCustomer>> findAll(){
        return ResponseEntity.ok(businessCustomerService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BusinessCustomer> findById(@PathVariable String id){
        Optional<BusinessCustomer> optional = businessCustomerService.findById(id);
        return optional.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/documentNumber/{documentNumber}")
    public ResponseEntity<BusinessCustomer> findByDocumentNumber(@PathVariable String documentNumber){
        Optional<BusinessCustomer> optional = businessCustomerService.findByDocumentNumber(documentNumber);
        return optional.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/")
    public ResponseEntity<BusinessCustomer> save(@RequestBody BusinessCustomer personalCustomer){
        return ResponseEntity.ok(businessCustomerService.save(personalCustomer));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BusinessCustomer> update(@RequestBody BusinessCustomer personalCustomer){
        Optional<BusinessCustomer> optional = businessCustomerService.findById(personalCustomer.getId());
        return optional.map(
                businessCustomer -> {
                    BusinessCustomer customer = new BusinessCustomer();
                    customer.setId(businessCustomer.getId());
                    return ResponseEntity.ok(businessCustomerService.save(customer));
                }
        ).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable String id){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
