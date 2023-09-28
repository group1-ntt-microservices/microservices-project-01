package com.ntt.microservice.customers.domain.service;

import java.util.List;
import java.util.Optional;

public interface BaseCustomerService<T, ID> {
    List<T> findAll();
    Optional<T> findById(ID id);
    Optional<T> findByDocumentNumber(String documentNumber);
    T save(T entity);
    boolean existsByDocumentNumber(String documentNumber);
    void deleteById(ID id);
}
