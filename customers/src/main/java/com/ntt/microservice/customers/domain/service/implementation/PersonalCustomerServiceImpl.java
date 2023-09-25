package com.ntt.microservice.customers.domain.service.implementation;

import com.ntt.microservice.customers.domain.model.PersonalCustomer;
import com.ntt.microservice.customers.domain.repository.PersonalCustomerRepository;
import com.ntt.microservice.customers.domain.service.PersonalCustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PersonalCustomerServiceImpl implements PersonalCustomerService {

    @Autowired
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
        personalCustomer.setId(UUID.randomUUID().toString());
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
