package com.ntt.microservice.customers.domain.repository;

import com.ntt.microservice.customers.domain.model.PersonalCustomer;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing personal customer entities.
 */
@Repository
public interface PersonalCustomerRepository
    extends BaseCustomerRepository<PersonalCustomer, String> {
}
