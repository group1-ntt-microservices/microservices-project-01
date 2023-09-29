package com.ntt.microservice.credits.service;

import com.ntt.microservice.credits.api.dto.request.CreditCardRequestDto;
import com.ntt.microservice.credits.domain.model.CreditCard;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CreditCardMapper {

  CreditCard creditCardRequestDtoToCreditCard(CreditCardRequestDto creditCardRequestDto);
}
