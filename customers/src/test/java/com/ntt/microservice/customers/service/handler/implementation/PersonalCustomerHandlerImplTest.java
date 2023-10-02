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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class PersonalCustomerHandlerImplTest {

  @Mock
  private PersonalCustomerMapper customerMapper;

  @Mock
  private PersonalCustomerService personalCustomerService;

  @InjectMocks
  private PersonalCustomerHandler personalCustomerHandler = new PersonalCustomerHandlerImpl(personalCustomerService, customerMapper);

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testFindAll() {
    when(personalCustomerService.findAll()).thenReturn(Arrays.asList(new PersonalCustomer(), new PersonalCustomer()));

    when(customerMapper.personalCustomerListToResponseDtoList(anyList())).thenReturn(Arrays.asList(new PersonalCustomerResponseDto(), new PersonalCustomerResponseDto()));

    List<PersonalCustomerResponseDto> result = personalCustomerHandler.findAll();

    assertEquals(2, result.size());
  }


  @Test
  void testFindById_CustomerFound() {
    PersonalCustomer personalCustomer = new PersonalCustomer();
    when(personalCustomerService.findById("1")).thenReturn(Optional.of(personalCustomer));

    when(customerMapper.personalCustomerToResponseDto(personalCustomer)).thenReturn(new PersonalCustomerResponseDto());

    PersonalCustomerResponseDto result = personalCustomerHandler.findById("1");

    assertNotNull(result);
  }

  @Test
  void testFindById_CustomerNotFound() {
    when(personalCustomerService.findById(anyString())).thenReturn(Optional.empty());

    assertThrows(CustomerNotFoundException.class, () -> personalCustomerHandler.findById("1"));
  }

  @Test
  void testFindByDocumentNumber_CustomerFound() {
    PersonalCustomer personalCustomer = new PersonalCustomer();
    when(personalCustomerService.findByDocumentNumber("12345")).thenReturn(Optional.of(personalCustomer));

    when(customerMapper.personalCustomerToResponseDto(personalCustomer)).thenReturn(new PersonalCustomerResponseDto());

    PersonalCustomerResponseDto result = personalCustomerHandler.findByDocumentNumber("12345");

    assertNotNull(result);
  }

  @Test
  void testFindByDocumentNumber_CustomerNotFound() {
    when(personalCustomerService.findByDocumentNumber(anyString())).thenReturn(Optional.empty());

    assertThrows(CustomerNotFoundException.class, () -> personalCustomerHandler.findByDocumentNumber("12345"));
  }

  @Test
  void testSave() {
    PersonalCustomerRequestDto request = new PersonalCustomerRequestDto();
    request.setDocumentNumber("123456");

    when(personalCustomerService.existsByDocumentNumber("123456")).thenReturn(false);

    PersonalCustomer personalCustomer = new PersonalCustomer();
    when(customerMapper.requestDtoToPersonalCustomer(request)).thenReturn(personalCustomer);

    PersonalCustomer savedCustomer = new PersonalCustomer();
    savedCustomer.setId(UUID.randomUUID().toString());
    savedCustomer.setActive(true);
    savedCustomer.setCreatedAt(new Date());

    when(personalCustomerService.save(personalCustomer)).thenReturn(savedCustomer);
    when(customerMapper.personalCustomerToResponseDto(savedCustomer)).thenReturn(new PersonalCustomerResponseDto());

    PersonalCustomerResponseDto result = personalCustomerHandler.save(request);

    assertNotNull(result);
    assertEquals(savedCustomer.getName(), result.getName());
  }


  @Test
  void testSave_DocumentNumberAlreadyExists() {
    PersonalCustomerRequestDto request = new PersonalCustomerRequestDto();
    request.setDocumentNumber("123456");

    when(personalCustomerService.existsByDocumentNumber("123456")).thenReturn(true);

    assertThrows(DocumentNumberAlreadyExistsException.class, () -> personalCustomerHandler.save(request));
  }

  @Test
  void testUpdate_CustomerNotFound() {
    String id = "testId";
    PersonalCustomerRequestDto request = new PersonalCustomerRequestDto();

    when(personalCustomerService.findById(id)).thenReturn(Optional.empty());

    assertThrows(CustomerNotFoundException.class, () -> personalCustomerHandler.update(id, request));
  }

  @Test
  void testUpdate_DocumentNumberDoesNotMatch() {
    String id = "testId";
    PersonalCustomerRequestDto request = new PersonalCustomerRequestDto();
    request.setDocumentNumber("123456");

    PersonalCustomer existingCustomer = new PersonalCustomer();
    existingCustomer.setDocumentNumber("654321");

    when(personalCustomerService.findById(id)).thenReturn(Optional.of(existingCustomer));

    assertThrows(DifferentDocumentNumberException.class, () -> personalCustomerHandler.update(id, request));
  }

  @Test
  void testDeleteById_CustomerFound() {
    String id = "testId";
    PersonalCustomer existingCustomer = new PersonalCustomer();

    when(personalCustomerService.findById(id)).thenReturn(Optional.of(existingCustomer));

    personalCustomerHandler.deleteById(id);

    verify(personalCustomerService).deleteById(id);
  }


  @Test
  void testDeleteById_CustomerNotFound() {
    String id = "testId";

    when(personalCustomerService.findById(id)).thenReturn(Optional.empty());

    assertThrows(CustomerNotFoundException.class, () -> personalCustomerHandler.deleteById(id));
  }
}
