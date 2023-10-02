package com.ntt.microservice.customers.domain.service.implementation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ntt.microservice.customers.domain.model.PersonalCustomer;
import com.ntt.microservice.customers.domain.repository.PersonalCustomerRepository;
import com.ntt.microservice.customers.domain.service.implementation.PersonalCustomerServiceImpl;
import com.ntt.microservice.customers.utils.CustomersUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;


class PersonalCustomerServiceImplTest {

  @InjectMocks
  private PersonalCustomerServiceImpl personalCustomerService;

  @Mock
  private PersonalCustomerRepository personalCustomerRepository;

  private PersonalCustomer personalCustomer;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    personalCustomer = CustomersUtils.getPersonalCustomer();
  }

  @Test
  void findAll() {
    when(personalCustomerRepository.findAll()).thenReturn(List.of(personalCustomer));
    List<PersonalCustomer> result = personalCustomerService.findAll();
    assertEquals(1, result.size());
    verify(personalCustomerRepository).findAll();
  }

  @Test
  void findById() {
    String id = "1";
    when(personalCustomerRepository.findById(id)).thenReturn(Optional.of(personalCustomer));
    Optional<PersonalCustomer> result = personalCustomerService.findById(id);
    assertTrue(result.isPresent());
    assertEquals(personalCustomer, result.get());
  }

  @Test
  void findByDocumentNumber() {
    String docNumber = "123";
    when(personalCustomerRepository.findByDocumentNumber(docNumber)).thenReturn(Optional.of(personalCustomer));
    Optional<PersonalCustomer> result = personalCustomerService.findByDocumentNumber(docNumber);
    assertTrue(result.isPresent());
    assertEquals(personalCustomer, result.get());
  }

  @Test
  void save() {
    when(personalCustomerRepository.save(personalCustomer)).thenReturn(personalCustomer);
    PersonalCustomer result = personalCustomerService.save(personalCustomer);
    assertEquals(personalCustomer, result);
  }

  @Test
  void deleteById() {
    String id = "1";
    personalCustomerService.deleteById(id);
    verify(personalCustomerRepository).deleteById(id);
  }

  @Test
  void existsByDocumentNumber() {
    String docNumber = "123";
    when(personalCustomerRepository.existsByDocumentNumber(docNumber)).thenReturn(true);
    boolean result = personalCustomerService.existsByDocumentNumber(docNumber);
    assertTrue(result);
    verify(personalCustomerRepository).existsByDocumentNumber(docNumber);
  }
}
