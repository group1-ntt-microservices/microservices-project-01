package com.ntt.microservice.customers.service.mapper;

import com.ntt.microservice.customers.api.dto.request.PersonalCustomerRequestDto;
import com.ntt.microservice.customers.api.dto.response.PersonalCustomerResponseDto;
import com.ntt.microservice.customers.domain.model.PersonalCustomer;
import java.util.List;
import org.mapstruct.Mapper;

/**
 * Mapper interface for converting personal customer entities to DTOs and vice versa.
 */
@Mapper(componentModel = "spring")
public interface PersonalCustomerMapper {

  /**
   * Maps a personal customer entity to a response DTO.
   *
   * @param personalCustomer The personal customer entity.
   * @return PersonalCustomerResponseDto containing the mapped data.
   */
  PersonalCustomerResponseDto personalCustomerToResponseDto(PersonalCustomer personalCustomer);

  /**
   * Maps a list of personal customer entities to a list of response DTOs.
   *
   * @param personalCustomers List of personal customer entities.
   * @return List of PersonalCustomerResponseDto containing the mapped data.
   */
  List<PersonalCustomerResponseDto> personalCustomerListToResponseDtoList(
      List<PersonalCustomer> personalCustomers);

  /**
   * Maps a request DTO to a personal customer entity.
   *
   * @param personalCustomerRequestDto The request DTO.
   * @return PersonalCustomer containing the mapped data.
   */
  PersonalCustomer requestDtoToPersonalCustomer(
      PersonalCustomerRequestDto personalCustomerRequestDto);
}
