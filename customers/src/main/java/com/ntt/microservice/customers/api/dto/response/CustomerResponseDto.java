package com.ntt.microservice.customers.api.dto.response;

import lombok.Getter;
import lombok.Setter;

/**
 * Data Transfer Object for representing customer response information.
 */
@Getter
@Setter
public class CustomerResponseDto {
  private String id;
  private String documentNumber;
  private String customerType;
  private String name;
  private String address;
  private String phone;
  private String mail;
  private boolean isActive;
}
