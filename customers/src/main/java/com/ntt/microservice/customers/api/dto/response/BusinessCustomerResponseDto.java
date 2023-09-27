package com.ntt.microservice.customers.api.dto.response;

import lombok.Getter;
import lombok.Setter;

/**
 * Data Transfer Object for representing business customer response information.
 */
@Getter
@Setter
public class BusinessCustomerResponseDto extends CustomerResponseDto {
  private String businessSector;
  private String representativeName;
  private String representativeDocumentNumber;
}
