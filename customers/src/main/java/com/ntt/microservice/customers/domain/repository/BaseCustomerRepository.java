package com.ntt.microservice.customers.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

@NoRepositoryBean
public interface BaseCustomerRepository<T, ID> extends JpaRepository<T, ID> {
    boolean existsByDocumentNumber(String documentNumber);
    Optional<T> findByDocumentNumber(String documentNumber);
}
