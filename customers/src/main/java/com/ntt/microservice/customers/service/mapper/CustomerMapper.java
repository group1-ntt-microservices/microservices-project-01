package com.ntt.microservice.customers.service.mapper;

import com.ntt.microservice.customers.api.dto.response.CustomerResponseDto;
import com.ntt.microservice.customers.domain.model.BusinessCustomer;
import com.ntt.microservice.customers.domain.model.PersonalCustomer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper interface for converting customer entities to DTOs and vice versa.
 */
@Mapper(componentModel = "spring")
public interface CustomerMapper {

  /**
   * Maps a personal customer entity to a DTO.
   *
   * @param customer The personal customer entity.
   * @return CustomerResponseDto containing the mapped data.
   */
  @Mapping(target = "name", expression = "java(customer.getName() + ' ' + customer.getLastName())")
  CustomerResponseDto personalCustomerToDto(PersonalCustomer customer);

  /**
   * Maps a business customer entity to a DTO.
   *
   * @param customer The business customer entity.
   * @return CustomerResponseDto containing the mapped data.
   */
  @Mapping(target = "name", source = "businessName")
  CustomerResponseDto businessCustomerToDto(BusinessCustomer customer);
}
