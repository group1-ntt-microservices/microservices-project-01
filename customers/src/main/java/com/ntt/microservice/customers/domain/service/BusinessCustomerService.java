package com.ntt.microservice.customers.domain.service;

import com.ntt.microservice.customers.domain.model.BusinessCustomer;

import java.util.List;
import java.util.Optional;

public interface BusinessCustomerService {
    List<BusinessCustomer> findAll();
    Optional<BusinessCustomer> findById(String id);
    Optional<BusinessCustomer> findByDocumentNumber(String documentNumber);
    BusinessCustomer save(BusinessCustomer businessCustomer);
    void deleteById(String id);
    boolean existsByDocumentNumber(String id);
}
