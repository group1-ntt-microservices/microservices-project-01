package com.ntt.microservice.customers.service.handler.implementation;

import com.ntt.microservice.customers.api.dto.response.CustomerResponseDto;
import com.ntt.microservice.customers.domain.model.BusinessCustomer;
import com.ntt.microservice.customers.domain.model.Customer;
import com.ntt.microservice.customers.domain.model.PersonalCustomer;
import com.ntt.microservice.customers.domain.service.CustomerService;
import com.ntt.microservice.customers.service.exception.CustomerNotFoundException;
import com.ntt.microservice.customers.service.handler.CustomerHandler;
import com.ntt.microservice.customers.service.mapper.CustomerMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class CustomerHandlerImpl implements CustomerHandler {
    private CustomerMapper customerMapper;
    private CustomerService customerService;

    @Override
    public List<CustomerResponseDto> findAll() {
        return customerService.findAll().stream()
                .map(this::mapCustomerToDto)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public CustomerResponseDto findById(String id) {
        return mapCustomerToDto(customerService.findById(id)
                .orElseThrow(CustomerNotFoundException::new));
    }

    @Override
    public CustomerResponseDto findByDocumentNumber(String documentNumber) {
        return mapCustomerToDto(customerService.findByDocumentNumber(documentNumber)
                .orElseThrow(CustomerNotFoundException::new));
    }

    private CustomerResponseDto mapCustomerToDto(Customer customer) {
        if (customer instanceof PersonalCustomer) {
            return customerMapper.personalCustomerToDto((PersonalCustomer) customer);
        } else if (customer instanceof BusinessCustomer) {
            return customerMapper.businessCustomerToDto((BusinessCustomer) customer);
        }
        throw new CustomerNotFoundException();
    }
}
