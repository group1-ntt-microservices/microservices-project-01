package com.ntt.microservice.customers.domain.repository;

import com.ntt.microservice.customers.domain.model.BusinessCustomer;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing business customer entities.
 */
@Repository
public interface BusinessCustomerRepository extends
    BaseCustomerRepository<BusinessCustomer, String> {
}
