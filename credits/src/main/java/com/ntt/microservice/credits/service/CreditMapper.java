package com.ntt.microservice.credits.service;

import com.ntt.microservice.credits.api.dto.request.CreditRequestDto;
import com.ntt.microservice.credits.domain.model.Credit;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CreditMapper {

  Credit creditRequestDtoToCredit(CreditRequestDto creditRequestDto);
}
