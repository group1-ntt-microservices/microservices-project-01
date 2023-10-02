package com.ntt.microservice.customers.domain.service.implementation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ntt.microservice.customers.domain.model.Customer;
import com.ntt.microservice.customers.domain.repository.CustomerRepository;
import com.ntt.microservice.customers.domain.service.implementation.CustomerServiceImpl;
import com.ntt.microservice.customers.utils.CustomersUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;


class CustomerServiceImplTest {
  @InjectMocks
  private CustomerServiceImpl customerService;

  @Mock
  private CustomerRepository customerRepository;

  private Customer customer;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    customer = CustomersUtils.getCustomer();
  }

  @Test
  void findAll() {
    when(customerRepository.findAll()).thenReturn(List.of(customer));
    List<Customer> result = customerService.findAll();
    assertEquals(1, result.size());
    verify(customerRepository).findAll();
  }

  @Test
  void findById() {
    String id = "1";
    when(customerRepository.findById(id)).thenReturn(Optional.of(customer));

    Optional<Customer> result = customerService.findById(id);
    assertTrue(result.isPresent());
    assertEquals(customer, result.get());
  }

  @Test
  void findByDocumentNumber() {
    String docNumber = "123";
    when(customerRepository.findByDocumentNumber(docNumber)).thenReturn(Optional.of(customer));
    Optional<Customer> result = customerService.findByDocumentNumber(docNumber);
    assertTrue(result.isPresent());
    assertEquals(customer, result.get());
  }
}
