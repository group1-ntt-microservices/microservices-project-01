package com.ntt.microservice.customers.domain.repository;

import com.ntt.microservice.customers.domain.model.Customer;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing customer entities.
 */
@Repository
public interface CustomerRepository extends BaseCustomerRepository<Customer, String> {
}
