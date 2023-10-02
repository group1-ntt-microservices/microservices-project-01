package com.ntt.microservice.customers.service.handler.implementation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;

import com.ntt.microservice.customers.api.dto.response.CustomerResponseDto;
import com.ntt.microservice.customers.domain.model.BusinessCustomer;
import com.ntt.microservice.customers.domain.model.Customer;
import com.ntt.microservice.customers.domain.model.PersonalCustomer;
import com.ntt.microservice.customers.domain.service.CustomerService;
import com.ntt.microservice.customers.service.exception.CustomerNotFoundException;
import com.ntt.microservice.customers.service.handler.CustomerHandler;
import com.ntt.microservice.customers.service.mapper.CustomerMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


class CustomerHandlerImplTest {

  @Mock
  private CustomerMapper customerMapper;

  @Mock
  private CustomerService customerService;

  @InjectMocks
  private CustomerHandler customerHandler = new CustomerHandlerImpl(customerMapper, customerService);

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testFindAll() {
    List<Customer> customers = Arrays.asList(new PersonalCustomer(), new BusinessCustomer());
    when(customerService.findAll()).thenReturn(customers);

    when(customerMapper.personalCustomerToDto(any(PersonalCustomer.class))).thenReturn(new CustomerResponseDto());
    when(customerMapper.businessCustomerToDto(any(BusinessCustomer.class))).thenReturn(new CustomerResponseDto());

    List<CustomerResponseDto> result = customerHandler.findAll();

    assertEquals(2, result.size());
  }

  @Test
  void testFindById_CustomerFound() {
    when(customerService.findById("1")).thenReturn(Optional.of(new PersonalCustomer()));

    when(customerMapper.personalCustomerToDto(any(PersonalCustomer.class))).thenReturn(new CustomerResponseDto());

    CustomerResponseDto result = customerHandler.findById("1");

    assertNotNull(result);
    assertEquals(CustomerResponseDto.class, result.getClass());
  }

  @Test
  void testFindById_CustomerNotFound() {
    when(customerService.findById(anyString())).thenReturn(Optional.empty());

    assertThrows(CustomerNotFoundException.class, () -> customerHandler.findById("1"));
  }

  @Test
  void testFindByDocumentNumber_CustomerFound() {
    when(customerService.findByDocumentNumber("12345")).thenReturn(Optional.of(new PersonalCustomer()));

    when(customerMapper.personalCustomerToDto(any(PersonalCustomer.class))).thenReturn(new CustomerResponseDto());

    CustomerResponseDto result = customerHandler.findByDocumentNumber("12345");

    assertNotNull(result);
    assertEquals(CustomerResponseDto.class, result.getClass());
  }

  @Test
  void testFindByDocumentNumber_CustomerNotFound() {
    when(customerService.findByDocumentNumber(anyString())).thenReturn(Optional.empty());

    assertThrows(CustomerNotFoundException.class, () -> customerHandler.findByDocumentNumber("12345"));
  }
}