package com.ntt.microservice.credits.service.mapper;

import com.ntt.microservice.credits.api.dto.request.CreditCardRequestDto;
import com.ntt.microservice.credits.api.dto.response.CreditCardResponseDto;
import com.ntt.microservice.credits.domain.model.CreditCard;
import org.mapstruct.Mapper;

/**
 * Mapper for converting CreditCard DTOs and entities.
 */
@Mapper(componentModel = "spring")
public interface CreditCardMapper {

  /**
   * Converts a CreditCardRequestDto to a CreditCard entity.
   *
   * @param creditCardRequestDto The CreditCardRequestDto to convert.
   * @return The resulting CreditCard entity.
   */
  CreditCard creditCardRequestDtoToCreditCard(CreditCardRequestDto creditCardRequestDto);

  /**
   * Converts a CreditCard entity to a CreditCardResponseDto.
   *
   * @param creditCard The CreditCard entity to convert.
   * @return The resulting CreditCardResponseDto.
   */
  CreditCardResponseDto creditCardToCreditCardResponseDto(CreditCard creditCard);
}
