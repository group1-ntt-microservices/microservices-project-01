package com.ntt.microservice.customers.utils;

import com.ntt.microservice.customers.api.dto.request.BusinessCustomerRequestDto;
import com.ntt.microservice.customers.api.dto.request.PersonalCustomerRequestDto;
import com.ntt.microservice.customers.domain.model.BusinessCustomer;
import com.ntt.microservice.customers.domain.model.Customer;
import com.ntt.microservice.customers.domain.model.PersonalCustomer;
import java.util.Date;

public class CustomersUtils {
  public static BusinessCustomerRequestDto getBusinessCustomerRequest() {
    BusinessCustomerRequestDto requestDto = new BusinessCustomerRequestDto();
    requestDto.setDocumentNumber("11111111111");
    requestDto.setBusinessName("someBusinessName");
    requestDto.setBusinessSector("someBusinessSector");
    requestDto.setRepresentativeName("someRepresentativeName");
    requestDto.setRepresentativeDocumentNumber("23123222");
    requestDto.setAddress("someRepresentativeEmail");
    requestDto.setPhone("12313131313");
    requestDto.setMail("someRepresentativl@mail.com");
    return requestDto;
  }

  public static BusinessCustomerRequestDto getBusinessCustomerRequestErrors() {
    BusinessCustomerRequestDto requestDto = new BusinessCustomerRequestDto();
    requestDto.setBusinessName("someBusinessName");
    requestDto.setBusinessSector("someBusinessSector");
    requestDto.setRepresentativeName("someRepresentativeName");
    requestDto.setRepresentativeDocumentNumber("23123222");
    requestDto.setAddress("someRepresentativeEmail");
    requestDto.setPhone("12313131313");
    requestDto.setMail("someRepresentativl@mail.com");
    return requestDto;
  }

  public static BusinessCustomer getBusinessCustomer() {
    BusinessCustomer businessCustomer = new BusinessCustomer();
    businessCustomer.setId("someId");
    businessCustomer.setDocumentNumber("11111111111");
    businessCustomer.setBusinessName("someBusinessName");
    businessCustomer.setBusinessSector("someBusinessSector");
    businessCustomer.setRepresentativeName("someRepresentativeName");
    businessCustomer.setRepresentativeDocumentNumber("23123222");
    businessCustomer.setAddress("someRepresentativeEmail");
    businessCustomer.setPhone("12313131313");
    businessCustomer.setMail("someRepresentativl@mail.com");
    return businessCustomer;
  }

  public static PersonalCustomerRequestDto getPersonalCustomerRequest() {
    PersonalCustomerRequestDto requestDto = new PersonalCustomerRequestDto();
    requestDto.setDocumentNumber("11111111");
    requestDto.setLastName("someLastName");
    requestDto.setName("someName");
    requestDto.setAddress("someAddress");
    requestDto.setPhone("12313131313");
    requestDto.setMail("someRepresentativl@mail.com");
    requestDto.setBirthDate(new Date());
    requestDto.setCivilStatus("someGender");
    return requestDto;
  }

  public static Customer getCustomer() {
    Customer requestDto = new Customer();
    requestDto.setId("someId");
    requestDto.setDocumentNumber("11111111");
    requestDto.setAddress("someAddress");
    requestDto.setPhone("12313131313");
    requestDto.setMail("someRepresentativl@mail.com");
    requestDto.setCreatedAt(new Date());
    requestDto.setActive(true);
    requestDto.setCustomerType("someCustomerType");
    return requestDto;
  }

  public static PersonalCustomer getPersonalCustomer() {
    PersonalCustomer personalCustomer = new PersonalCustomer();
    personalCustomer.setId("someId");
    personalCustomer.setDocumentNumber("11111111");
    personalCustomer.setLastName("someLastName");
    personalCustomer.setName("someName");
    personalCustomer.setAddress("someAddress");
    personalCustomer.setPhone("12313131313");
    personalCustomer.setMail("someRepresentativl@mail.com");
    personalCustomer.setCreatedAt(new Date());
    personalCustomer.setActive(true);
    personalCustomer.setCustomerType("someCustomerType");
    personalCustomer.setBirthDate(new Date());
    personalCustomer.setCivilStatus("someGender");
    return personalCustomer;
  }
}
