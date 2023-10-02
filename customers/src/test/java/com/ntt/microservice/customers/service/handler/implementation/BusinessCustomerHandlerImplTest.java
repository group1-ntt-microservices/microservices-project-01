package com.ntt.microservice.customers.service.handler.implementation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ntt.microservice.customers.api.dto.request.BusinessCustomerRequestDto;
import com.ntt.microservice.customers.api.dto.response.BusinessCustomerResponseDto;
import com.ntt.microservice.customers.domain.model.BusinessCustomer;
import com.ntt.microservice.customers.domain.service.BusinessCustomerService;
import com.ntt.microservice.customers.service.exception.CustomerNotFoundException;
import com.ntt.microservice.customers.service.exception.DifferentDocumentNumberException;
import com.ntt.microservice.customers.service.exception.DocumentNumberAlreadyExistsException;
import com.ntt.microservice.customers.service.handler.implementation.BusinessCustomerHandlerImpl;
import com.ntt.microservice.customers.service.mapper.BusinessCustomerMapper;
import java.util.Collections;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class BusinessCustomerHandlerImplTest {
  @Mock
  private BusinessCustomerService businessCustomerService;

  @Mock
  private BusinessCustomerMapper businessCustomerMapper;

  @InjectMocks
  private BusinessCustomerHandlerImpl businessCustomerHandler;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void findAll() {
    when(businessCustomerService.findAll()).thenReturn(Collections.singletonList(new BusinessCustomer()));
    when(businessCustomerMapper.businessCustomerListToResponseDtoList(any()))
        .thenReturn(Collections.singletonList(new BusinessCustomerResponseDto()));

    businessCustomerHandler.findAll();

    verify(businessCustomerService).findAll();
    verify(businessCustomerMapper).businessCustomerListToResponseDtoList(any());
  }

  @Test
  void findById_whenCustomerExists() {
    when(businessCustomerService.findById(anyString())).thenReturn(Optional.of(new BusinessCustomer()));
    when(businessCustomerMapper.businessCustomerToResponseDto(any())).thenReturn(new BusinessCustomerResponseDto());
    businessCustomerHandler.findById("1");

    verify(businessCustomerService).findById(anyString());
    verify(businessCustomerMapper).businessCustomerToResponseDto(any());
  }

  @Test
  void findById_whenCustomerNotFound() {
    when(businessCustomerService.findById(anyString())).thenReturn(Optional.empty());
    try {
      businessCustomerHandler.findById("1");
    } catch (CustomerNotFoundException e) {
    }

    verify(businessCustomerService).findById(anyString());
  }

  @Test
  void save_whenDocumentNumberDoesNotExist() {
    BusinessCustomerRequestDto request = new BusinessCustomerRequestDto();
    request.setDocumentNumber("123456");

    when(businessCustomerService.existsByDocumentNumber(anyString())).thenReturn(false);
    when(businessCustomerService.save(any())).thenReturn(new BusinessCustomer());
    when(businessCustomerMapper.requestDtoToBusinessCustomer(any())).thenReturn(new BusinessCustomer());
    when(businessCustomerMapper.businessCustomerToResponseDto(any())).thenReturn(new BusinessCustomerResponseDto());

    businessCustomerHandler.save(request);

    verify(businessCustomerService).save(any());
    verify(businessCustomerMapper).requestDtoToBusinessCustomer(any());
    verify(businessCustomerMapper).businessCustomerToResponseDto(any());
  }

  @Test
  void save_whenDocumentNumberAlreadyExists() {
    BusinessCustomerRequestDto request = new BusinessCustomerRequestDto();
    request.setDocumentNumber("123456");

    when(businessCustomerService.existsByDocumentNumber(anyString())).thenReturn(true);

    try {
      businessCustomerHandler.save(request);
    } catch (DocumentNumberAlreadyExistsException e) {
      // Expected exception
    }

    verify(businessCustomerService, never()).save(any());
    verify(businessCustomerMapper, never()).requestDtoToBusinessCustomer(any());
    verify(businessCustomerMapper, never()).businessCustomerToResponseDto(any());
  }

  @Test
  void update_whenCustomerExistsAndDocumentNumberMatches() {
    String id = "testId";
    BusinessCustomerRequestDto request = new BusinessCustomerRequestDto();
    request.setDocumentNumber("123456");

    BusinessCustomer existingBusinessCustomer = new BusinessCustomer();
    existingBusinessCustomer.setDocumentNumber("123456");

    when(businessCustomerService.findById(anyString())).thenReturn(Optional.of(existingBusinessCustomer));
    when(businessCustomerService.save(any())).thenReturn(new BusinessCustomer());
    when(businessCustomerMapper.requestDtoToBusinessCustomer(any())).thenReturn(new BusinessCustomer());
    when(businessCustomerMapper.businessCustomerToResponseDto(any())).thenReturn(new BusinessCustomerResponseDto());

    businessCustomerHandler.update(id, request);

    verify(businessCustomerService).save(any());
    verify(businessCustomerMapper).requestDtoToBusinessCustomer(any());
    verify(businessCustomerMapper).businessCustomerToResponseDto(any());
  }

  @Test
  void update_whenCustomerNotFound() {
    String id = "testId";
    BusinessCustomerRequestDto request = new BusinessCustomerRequestDto();

    when(businessCustomerService.findById(anyString())).thenReturn(Optional.empty());

    try {
      businessCustomerHandler.update(id, request);
    } catch (CustomerNotFoundException e) {
      // Expected exception
    }

    verify(businessCustomerService, never()).save(any());
    verify(businessCustomerMapper, never()).requestDtoToBusinessCustomer(any());
    verify(businessCustomerMapper, never()).businessCustomerToResponseDto(any());
  }

  @Test
  void update_whenDocumentNumberDoesNotMatch() {
    String id = "testId";
    BusinessCustomerRequestDto request = new BusinessCustomerRequestDto();
    request.setDocumentNumber("123456");

    BusinessCustomer existingBusinessCustomer = new BusinessCustomer();
    existingBusinessCustomer.setDocumentNumber("654321");

    when(businessCustomerService.findById(anyString())).thenReturn(Optional.of(existingBusinessCustomer));

    try {
      businessCustomerHandler.update(id, request);
    } catch (DifferentDocumentNumberException e) {
      // Expected exception
    }

    verify(businessCustomerService, never()).save(any());
    verify(businessCustomerMapper, never()).requestDtoToBusinessCustomer(any());
    verify(businessCustomerMapper, never()).businessCustomerToResponseDto(any());
  }

  @Test
  void deleteById_whenCustomerExists() {
    String id = "testId";

    when(businessCustomerService.findById(anyString())).thenReturn(Optional.of(new BusinessCustomer()));

    businessCustomerHandler.deleteById(id);

    verify(businessCustomerService).deleteById(anyString());
  }

  @Test
  void deleteById_whenCustomerNotFound() {
    String id = "testId";

    when(businessCustomerService.findById(anyString())).thenReturn(Optional.empty());

    try {
      businessCustomerHandler.deleteById(id);
    } catch (CustomerNotFoundException e) {
      // Expected exception
    }

    verify(businessCustomerService, never()).deleteById(anyString());
  }

  @Test
  void findByDocumentNumber_whenCustomerExists_returnsCustomer() {

    String documentNumber = "123456";
    BusinessCustomer businessCustomer = new BusinessCustomer();
    BusinessCustomerResponseDto expectedResponse = new BusinessCustomerResponseDto();

    when(businessCustomerService.findByDocumentNumber(documentNumber))
        .thenReturn(Optional.of(businessCustomer));
    when(businessCustomerMapper.businessCustomerToResponseDto(businessCustomer))
        .thenReturn(expectedResponse);

    BusinessCustomerResponseDto actualResponse = businessCustomerHandler.findByDocumentNumber(documentNumber);

    assertEquals(expectedResponse, actualResponse);
    verify(businessCustomerService).findByDocumentNumber(documentNumber);
    verify(businessCustomerMapper).businessCustomerToResponseDto(businessCustomer);
  }

  @Test
  void findByDocumentNumber_whenCustomerDoesNotExist_throwsException() {

    String documentNumber = "123456";
    when(businessCustomerService.findByDocumentNumber(documentNumber))
        .thenReturn(Optional.empty());

    assertThrows(CustomerNotFoundException.class, () ->
        businessCustomerHandler.findByDocumentNumber(documentNumber)
    );
    verify(businessCustomerService).findByDocumentNumber(documentNumber);
    verify(businessCustomerMapper, never()).businessCustomerToResponseDto(any());
  }
}
