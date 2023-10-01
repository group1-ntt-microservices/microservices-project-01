package com.ntt.microservice.customers.domain.service.implementation;

import com.ntt.microservice.customers.domain.model.BusinessCustomer;
import com.ntt.microservice.customers.domain.repository.BusinessCustomerRepository;
import com.ntt.microservice.customers.domain.service.BusinessCustomerService;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Implementation of Business Customer Service interface.
 */
@Service
@AllArgsConstructor
public class BusinessCustomerServiceImpl implements BusinessCustomerService {

  private BusinessCustomerRepository businessCustomerRepository;

  @Override
  public List<BusinessCustomer> findAll() {
    return businessCustomerRepository.findAll();
  }

  @Override
  public Optional<BusinessCustomer> findById(String id) {
    return businessCustomerRepository.findById(id);
  }

  @Override
  public Optional<BusinessCustomer> findByDocumentNumber(String documentNumber) {
    return businessCustomerRepository.findByDocumentNumber(documentNumber);
  }

  @Override
  public BusinessCustomer save(BusinessCustomer businessCustomer) {
    return businessCustomerRepository.save(businessCustomer);
  }

  @Override
  public void deleteById(String id) {
    businessCustomerRepository.deleteById(id);
  }

  @Override
  public boolean existsByDocumentNumber(String id) {
    return businessCustomerRepository.existsByDocumentNumber(id);
  }
}
