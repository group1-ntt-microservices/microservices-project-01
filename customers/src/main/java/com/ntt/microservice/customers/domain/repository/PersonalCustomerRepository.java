package com.ntt.microservice.customers.domain.repository;

import com.ntt.microservice.customers.domain.model.PersonalCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonalCustomerRepository extends JpaRepository<PersonalCustomer, String> {
    boolean existsByDocumentNumber(String id);
    Optional<PersonalCustomer> findByDocumentNumber(String documentNumber);
}
