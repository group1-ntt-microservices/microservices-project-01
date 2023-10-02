package com.ntt.microservice.customers.domain.service.implementation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ntt.microservice.customers.domain.model.BusinessCustomer;
import com.ntt.microservice.customers.domain.repository.BusinessCustomerRepository;
import com.ntt.microservice.customers.domain.service.implementation.BusinessCustomerServiceImpl;
import com.ntt.microservice.customers.utils.CustomersUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;


class BusinessCustomerServiceImplTest {

  @InjectMocks
  private BusinessCustomerServiceImpl businessCustomerService;

  @Mock
  private BusinessCustomerRepository businessCustomerRepository;

  private BusinessCustomer businessCustomer;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    businessCustomer = CustomersUtils.getBusinessCustomer();
  }

  @Test
  void findAll() {
    when(businessCustomerRepository.findAll()).thenReturn(List.of(businessCustomer));
    List<BusinessCustomer> result = businessCustomerService.findAll();
    assertEquals(1, result.size());
    verify(businessCustomerRepository).findAll();
  }

  @Test
  void findById() {
    String id = "1";
    when(businessCustomerRepository.findById(id)).thenReturn(Optional.of(businessCustomer));

    Optional<BusinessCustomer> result = businessCustomerService.findById(id);
    assertTrue(result.isPresent());
    assertEquals(businessCustomer, result.get());
  }

  @Test
  void findByDocumentNumber() {
    String docNumber = "123";
    when(businessCustomerRepository.findByDocumentNumber(docNumber)).thenReturn(Optional.of(businessCustomer));
    Optional<BusinessCustomer> result = businessCustomerService.findByDocumentNumber(docNumber);
    assertTrue(result.isPresent());
    assertEquals(businessCustomer, result.get());
  }

  @Test
  void save() {
    when(businessCustomerRepository.save(businessCustomer)).thenReturn(businessCustomer);
    BusinessCustomer result = businessCustomerService.save(businessCustomer);
    assertEquals(businessCustomer, result);
  }

  @Test
  void deleteById() {
    String id = "1";
    businessCustomerService.deleteById(id);
    verify(businessCustomerRepository).deleteById(id);
  }

  @Test
  void existsByDocumentNumber() {
    String docNumber = "123";
    when(businessCustomerRepository.existsByDocumentNumber(docNumber)).thenReturn(true);
    boolean result = businessCustomerService.existsByDocumentNumber(docNumber);
    assertTrue(result);
    verify(businessCustomerRepository).existsByDocumentNumber(docNumber);
  }

  @Test
  void notExistsByDocumentNumber() {
    String docNumber = "123";
    when(businessCustomerRepository.existsByDocumentNumber(docNumber)).thenReturn(false);
    boolean result = businessCustomerService.existsByDocumentNumber(docNumber);
    assertFalse(result);
    verify(businessCustomerRepository).existsByDocumentNumber(docNumber);
  }
}
