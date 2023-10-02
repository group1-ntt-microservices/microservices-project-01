package com.ntt.microservice.credits.feign.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * Data Transfer Object for representing customer information.
 */
@Getter
@Setter
public class CustomerDto {
  private String id;
  private String documentNumber;
  private String customerType;
  private String name;
  private String address;
  private String phone;
  private String mail;
  private boolean isActive;
}