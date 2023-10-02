package com.ntt.microservice.customers.service.handler;

import com.ntt.microservice.customers.api.dto.request.PersonalCustomerRequestDto;
import com.ntt.microservice.customers.api.dto.response.PersonalCustomerResponseDto;

/**
 * Interface for handling personal customer operations.
 */
public interface PersonalCustomerHandler
    extends BaseCustomerHandler<PersonalCustomerResponseDto, PersonalCustomerRequestDto> {
}
