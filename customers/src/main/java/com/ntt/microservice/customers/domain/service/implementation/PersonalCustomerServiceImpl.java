package com.ntt.microservice.customers.domain.service.implementation;

import com.ntt.microservice.customers.domain.model.PersonalCustomer;
import com.ntt.microservice.customers.domain.repository.PersonalCustomerRepository;
import com.ntt.microservice.customers.domain.service.PersonalCustomerService;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Implementation of Personal Customer Service interface.
 */
@AllArgsConstructor
@Service
public class PersonalCustomerServiceImpl implements PersonalCustomerService {

  private PersonalCustomerRepository personalCustomerRepository;

  @Override
  public List<PersonalCustomer> findAll() {
    return personalCustomerRepository.findAll();
  }

  @Override
  public Optional<PersonalCustomer> findById(String id) {
    return personalCustomerRepository.findById(id);
  }

  @Override
  public Optional<PersonalCustomer> findByDocumentNumber(String documentNumber) {
    return personalCustomerRepository.findByDocumentNumber(documentNumber);
  }

  @Override
  public PersonalCustomer save(PersonalCustomer personalCustomer) {
    return personalCustomerRepository.save(personalCustomer);
  }

  @Override
  public void deleteById(String id) {
    personalCustomerRepository.deleteById(id);
  }

  @Override
  public boolean existsByDocumentNumber(String id) {
    return personalCustomerRepository.existsByDocumentNumber(id);
  }
}
