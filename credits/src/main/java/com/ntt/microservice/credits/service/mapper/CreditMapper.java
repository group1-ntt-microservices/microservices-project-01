package com.ntt.microservice.credits.service.mapper;

import com.ntt.microservice.credits.api.dto.request.CreditRequestDto;
import com.ntt.microservice.credits.api.dto.response.CreditResponseDto;
import com.ntt.microservice.credits.domain.model.Credit;
import org.mapstruct.Mapper;

/**
 * Mapper for converting Credit DTOs and entities.
 */
@Mapper(componentModel = "spring")
public interface CreditMapper {

  /**
   * Converts a CreditRequestDto to a Credit entity.
   *
   * @param creditRequestDto The CreditRequestDto to convert.
   * @return The resulting Credit entity.
   */
  Credit creditRequestDtoToCredit(CreditRequestDto creditRequestDto);

  /**
   * Converts a Credit entity to a CreditResponseDto.
   *
   * @param credit The Credit entity to convert.
   * @return The resulting CreditResponseDto.
   */
  CreditResponseDto creditToCreditResponseDto(Credit credit);
}
