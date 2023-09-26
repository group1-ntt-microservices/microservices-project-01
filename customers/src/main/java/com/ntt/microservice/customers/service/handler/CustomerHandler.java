package com.ntt.microservice.customers.service.handler;

import com.ntt.microservice.customers.api.dto.response.CustomerResponseDto;

import java.util.List;

public interface CustomerHandler {
    List<CustomerResponseDto> findAll();
    CustomerResponseDto findById(String id);
    CustomerResponseDto findByDocumentNumber(String documentNumber);
}
