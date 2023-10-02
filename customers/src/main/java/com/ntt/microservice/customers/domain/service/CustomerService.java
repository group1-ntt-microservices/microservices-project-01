package com.ntt.microservice.customers.domain.service;

import com.ntt.microservice.customers.domain.model.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    List<Customer> findAll();
    Optional<Customer> findById(String id);
    Optional<Customer> findByDocumentNumber(String documentNumber);
}
