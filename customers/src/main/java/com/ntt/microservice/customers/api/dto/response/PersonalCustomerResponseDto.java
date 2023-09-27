package com.ntt.microservice.customers.api.dto.response;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

/**
 * Data Transfer Object for representing personal customer response information.
 */
@Getter
@Setter
public class PersonalCustomerResponseDto extends CustomerResponseDto {
  private String civilStatus;
  private LocalDate birthDate;
}
