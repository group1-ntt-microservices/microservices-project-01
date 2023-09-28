package com.ntt.microservice.customers.service.mapper;

import com.ntt.microservice.customers.api.dto.response.CustomerResponseDto;
import com.ntt.microservice.customers.domain.model.BusinessCustomer;
import com.ntt.microservice.customers.domain.model.PersonalCustomer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    @Mapping(target = "name", expression = "java(customer.getName() + ' ' + customer.getLastName())")
    CustomerResponseDto personalCustomerToDto(PersonalCustomer customer);

    @Mapping(target = "name", source = "businessName")
    CustomerResponseDto businessCustomerToDto(BusinessCustomer customer);
}
