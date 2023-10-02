package com.ntt.microservice.customers.service.handler;

import com.ntt.microservice.customers.api.dto.request.BusinessCustomerRequestDto;
import com.ntt.microservice.customers.api.dto.response.BusinessCustomerResponseDto;

/**
 * Interface for handling business customer operations.
 */
public interface BusinessCustomerHandler
    extends BaseCustomerHandler<BusinessCustomerResponseDto, BusinessCustomerRequestDto> {
}
