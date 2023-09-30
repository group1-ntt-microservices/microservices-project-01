package com.ntt.microservice.customers.domain.service.implementation;

import com.ntt.microservice.customers.domain.model.Customer;
import com.ntt.microservice.customers.domain.repository.CustomerRepository;
import com.ntt.microservice.customers.domain.service.CustomerService;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Implementation of Customer Service interface.
 */
@AllArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService {

  private CustomerRepository customerRepository;

  @Override
  public List<Customer> findAll() {
    return customerRepository.findAll();
  }

  @Override
  public Optional<Customer> findById(String id) {
    return customerRepository.findById(id);
  }

  @Override
  public Optional<Customer> findByDocumentNumber(String documentNumber) {
    return customerRepository.findByDocumentNumber(documentNumber);
  }
}
