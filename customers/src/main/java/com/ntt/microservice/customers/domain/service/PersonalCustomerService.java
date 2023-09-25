package com.ntt.microservice.customers.domain.service;

import com.ntt.microservice.customers.domain.model.PersonalCustomer;

import java.util.List;
import java.util.Optional;

public interface PersonalCustomerService {
    List<PersonalCustomer> findAll();
    Optional<PersonalCustomer> findById(String id);
    Optional<PersonalCustomer> findByDocumentNumber(String documentNumber);
    PersonalCustomer save(PersonalCustomer personalCustomer);
    void deleteById(String id);
    boolean existsByDocumentNumber(String id);
}
