package com.ntt.microservice.customers.api.dto.request;

import java.util.Date;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO for personal customer information with validation rules.
 */
@Getter
@Setter
public class PersonalCustomerRequestDto {

  @NotBlank(message = "Document number is required.")
  @Pattern(regexp = "^\\d{8}$",
      message = "Document number must be numeric and have 8 digits.")
  private String documentNumber;

  @NotBlank(message = "Name is required.")
  @Max(value = 50, message = "Name must be less than 50 characters.")
  @Min(value = 3, message = "Name must be more than 2 characters.")
  private String name;

  @NotBlank(message = "Last name is required.")
  @Max(value = 50, message = "Last name must be less than 50 characters.")
  @Min(value = 3, message = "Last name must be more than 2 characters.")
  private String lastName;

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

  @Past(message = "Date of birth must be in the past")
  private Date birthDate;

  @NotBlank(message = "Civil status is required.")
  private String civilStatus;
}
