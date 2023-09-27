package com.ntt.microservice.customers.service.handler.implementation;

import com.ntt.microservice.customers.api.dto.request.PersonalCustomerRequestDto;
import com.ntt.microservice.customers.api.dto.response.PersonalCustomerResponseDto;
import com.ntt.microservice.customers.domain.model.PersonalCustomer;
import com.ntt.microservice.customers.domain.service.PersonalCustomerService;
import com.ntt.microservice.customers.service.exception.CustomerNotFoundException;
import com.ntt.microservice.customers.service.exception.DifferentDocumentNumberException;
import com.ntt.microservice.customers.service.exception.DocumentNumberAlreadyExistsException;
import com.ntt.microservice.customers.service.handler.PersonalCustomerHandler;
import com.ntt.microservice.customers.service.mapper.PersonalCustomerMapper;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Implementation of Interface Personal Customer Handler, with validations and exceptions.
 */
@AllArgsConstructor
@Service
public class PersonalCustomerHandlerImpl implements PersonalCustomerHandler {

  private PersonalCustomerService personalCustomerService;
  private PersonalCustomerMapper customerMapper;

  @Override
  public List<PersonalCustomerResponseDto> findAll() {
    return customerMapper.personalCustomerListToResponseDtoList(personalCustomerService.findAll());
  }

  @Override
  public PersonalCustomerResponseDto findById(String id) {
    return personalCustomerService.findById(id)
        .map(
            personalCustomer -> customerMapper.personalCustomerToResponseDto(personalCustomer))
        .orElseThrow(CustomerNotFoundException::new);
  }

  @Override
  public PersonalCustomerResponseDto findByDocumentNumber(String documentNumber) {
    return personalCustomerService.findByDocumentNumber(documentNumber)
        .map(
            personalCustomer -> customerMapper.personalCustomerToResponseDto(personalCustomer))
        .orElseThrow(CustomerNotFoundException::new);
  }

  @Override
  public PersonalCustomerResponseDto save(PersonalCustomerRequestDto customer) {
    if (personalCustomerService.existsByDocumentNumber(customer.getDocumentNumber())) {
      throw new DocumentNumberAlreadyExistsException();
    }
    PersonalCustomer personalCustomer = customerMapper.requestDtoToPersonalCustomer(customer);
    personalCustomer.setId(UUID.randomUUID().toString());
    personalCustomer.setActive(true);
    personalCustomer.setCreatedAt(new Date());
    return customerMapper.personalCustomerToResponseDto(
        personalCustomerService.save(personalCustomer));
  }

  @Override
  public PersonalCustomerResponseDto update(
      String id,
      PersonalCustomerRequestDto customer) {
    Optional<PersonalCustomer> existingPersonalCustomer = personalCustomerService.findById(id);
    if (!existingPersonalCustomer.isPresent()) {
      throw new CustomerNotFoundException();
    }
    if (!existingPersonalCustomer.get().getDocumentNumber().equals(customer.getDocumentNumber())) {
      throw new DifferentDocumentNumberException();
    }
    PersonalCustomer personalCustomer = customerMapper.requestDtoToPersonalCustomer(customer);
    personalCustomer.setId(id);
    return customerMapper.personalCustomerToResponseDto(
        personalCustomerService.save(personalCustomer));

  }

  @Override
  public void deleteById(String id) {
    Optional<PersonalCustomer> existingPersonalCustomer = personalCustomerService.findById(id);
    if (!existingPersonalCustomer.isPresent()) {
      throw new CustomerNotFoundException();
    }
    personalCustomerService.deleteById(id);
  }
}
