package com.ntt.microservice.customers.service.handler.implementation;

import com.ntt.microservice.customers.api.dto.request.BusinessCustomerRequestDto;
import com.ntt.microservice.customers.api.dto.response.BusinessCustomerResponseDto;
import com.ntt.microservice.customers.domain.model.BusinessCustomer;
import com.ntt.microservice.customers.domain.service.BusinessCustomerService;
import com.ntt.microservice.customers.service.exception.CustomerNotFoundException;
import com.ntt.microservice.customers.service.exception.DifferentDocumentNumberException;
import com.ntt.microservice.customers.service.exception.DocumentNumberAlreadyExistsException;
import com.ntt.microservice.customers.service.handler.BusinessCustomerHandler;
import com.ntt.microservice.customers.service.mapper.BusinessCustomerMapper;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Implementation of Interface Business Customer Handler, with validations and exceptions.
 */
@AllArgsConstructor
@Service
public class BusinessCustomerHandlerImpl implements BusinessCustomerHandler {

  private BusinessCustomerService businessCustomerService;
  private BusinessCustomerMapper customerMapper;

  @Override
  public List<BusinessCustomerResponseDto> findAll() {
    return customerMapper.businessCustomerListToResponseDtoList(businessCustomerService.findAll());
  }

  @Override
  public BusinessCustomerResponseDto findById(String id) {
    return businessCustomerService.findById(id)
        .map(
            businessCustomer -> customerMapper.businessCustomerToResponseDto(businessCustomer))
        .orElseThrow(CustomerNotFoundException::new);
  }

  @Override
  public BusinessCustomerResponseDto findByDocumentNumber(String documentNumber) {
    return businessCustomerService.findByDocumentNumber(documentNumber)
        .map(
            businessCustomer -> customerMapper.businessCustomerToResponseDto(businessCustomer))
        .orElseThrow(CustomerNotFoundException::new);
  }

  @Override
  public BusinessCustomerResponseDto save(BusinessCustomerRequestDto customer) {
    if (businessCustomerService.existsByDocumentNumber(customer.getDocumentNumber())) {
      throw new DocumentNumberAlreadyExistsException();
    }
    BusinessCustomer businessCustomer = customerMapper.requestDtoToBusinessCustomer(customer);
    businessCustomer.setId(UUID.randomUUID().toString());
    businessCustomer.setActive(true);
    businessCustomer.setCreatedAt(new Date());
    return customerMapper.businessCustomerToResponseDto(
        businessCustomerService.save(businessCustomer));
  }

  @Override
  public BusinessCustomerResponseDto update(String id, BusinessCustomerRequestDto customer) {
    Optional<BusinessCustomer> existingBusinessCustomer = businessCustomerService.findById(id);
    if (!existingBusinessCustomer.isPresent()) {
      throw new CustomerNotFoundException();
    }
    if (!existingBusinessCustomer.get().getDocumentNumber().equals(customer.getDocumentNumber())) {
      throw new DifferentDocumentNumberException();
    }
    BusinessCustomer businessCustomer = customerMapper.requestDtoToBusinessCustomer(customer);
    businessCustomer.setId(id);
    return customerMapper.businessCustomerToResponseDto(
        businessCustomerService.save(businessCustomer));
  }

  @Override
  public void deleteById(String id) {
    Optional<BusinessCustomer> existingBusinessCustomer = businessCustomerService.findById(id);
    if (!existingBusinessCustomer.isPresent()) {
      throw new CustomerNotFoundException();
    }
    businessCustomerService.deleteById(id);
  }
}
