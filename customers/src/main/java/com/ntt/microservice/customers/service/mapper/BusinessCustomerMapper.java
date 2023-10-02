package com.ntt.microservice.customers.service.mapper;

import com.ntt.microservice.customers.api.dto.request.BusinessCustomerRequestDto;
import com.ntt.microservice.customers.api.dto.response.BusinessCustomerResponseDto;
import com.ntt.microservice.customers.domain.model.BusinessCustomer;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper interface for converting business customer entities to DTOs and vice versa.
 */
@Mapper(componentModel = "spring")
public interface BusinessCustomerMapper {

  /**
   * Maps a business customer entity to a response DTO.
   *
   * @param businessCustomer The business customer entity.
   * @return BusinessCustomerResponseDto containing the mapped data.
   */
  @Mapping(source = "businessName", target = "name")
  BusinessCustomerResponseDto businessCustomerToResponseDto(BusinessCustomer businessCustomer);

  /**
   * Maps a list of business customer entities to a list of response DTOs.
   *
   * @param businessCustomers List of business customer entities.
   * @return List of BusinessCustomerResponseDto containing the mapped data.
   */
  List<BusinessCustomerResponseDto> businessCustomerListToResponseDtoList(
      List<BusinessCustomer> businessCustomers);

  /**
   * Maps a request DTO to a business customer entity.
   *
   * @param businessCustomerRequestDto The request DTO.
   * @return BusinessCustomer containing the mapped data.
   */
  BusinessCustomer requestDtoToBusinessCustomer(
      BusinessCustomerRequestDto businessCustomerRequestDto);
}
