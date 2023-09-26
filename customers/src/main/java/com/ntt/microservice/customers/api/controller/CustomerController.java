package com.ntt.microservice.customers.api.controller;

import com.ntt.microservice.customers.api.dto.response.CustomerResponseDto;
import com.ntt.microservice.customers.service.handler.CustomerHandler;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
public class CustomerController {

    private CustomerHandler customerHandler;

    @GetMapping("/")
    public ResponseEntity<List<CustomerResponseDto>> findAll(){
        return ResponseEntity.ok(customerHandler.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponseDto> findById(@PathVariable String id){
        return ResponseEntity.ok(customerHandler.findById(id));
    }

    @GetMapping("/documentNumber/{documentNumber}")
    public ResponseEntity<CustomerResponseDto> findByDocumentNumber(@PathVariable String documentNumber){
        return ResponseEntity.ok(customerHandler.findByDocumentNumber(documentNumber));
    }
}
