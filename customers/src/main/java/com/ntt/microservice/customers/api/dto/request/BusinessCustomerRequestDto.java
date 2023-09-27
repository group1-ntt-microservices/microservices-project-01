package com.ntt.microservice.customers.api.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO for business customer information with validation rules.
 */
@Getter
@Setter
public class BusinessCustomerRequestDto {

  @NotBlank(message = "Document number is required.")
  @Pattern(regexp = "^\\d{11}$",
      message = "Document number must be numeric and have 11 digits.")
  private String documentNumber;

  @NotBlank(message = "business name is required.")
  @Max(value = 50, message = "business name must be less than 50 characters.")
  @Min(value = 3, message = "business name must be more than 2 characters.")
  private String businessName;

  @NotBlank(message = "business sector is required.")
  @Max(value = 50, message = "business sector must be less than 50 characters.")
  @Min(value = 3, message = "business sector must be more than 2 characters.")
  private String businessSector;

  @NotBlank(message = "representative name is required.")
  @Max(value = 50, message = "representative name must be less than 50 characters.")
  @Min(value = 3, message = "representative name must be more than 2 characters.")
  private String representativeName;

  @NotBlank(message = "representative document number is required.")
  @Pattern(regexp = "^\\d{8}$",
      message = "Document number must be numeric and have 8 digits.")
  private String representativeDocumentNumber;

  @NotBlank(message = "Address is required.")
  @Max(value = 50, message = "Address must be less than 50 characters.")
  @Min(value = 3, message = "Address must be more than 2 characters.")
  private String address;

  @NotBlank(message = "Phone is required.")
  @Pattern(regexp = "^\\+?\\d{1,12}$",
      message = "Phone number must be a sequence of up to 13 digits, "
          + "optionally starting with a '+' sign")
  private String phone;

  @NotBlank(message = "Mail is required.")
  @Email(message = "Invalid email format")
  private String mail;
}
