package com.ntt.microservice.customers.domain.repository;

import com.ntt.microservice.customers.domain.model.BusinessCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BusinessCustomerRepository extends JpaRepository<BusinessCustomer, String> {
    boolean existsByDocumentNumber(String id);
    Optional<BusinessCustomer> findByDocumentNumber(String documentNumber);
}
