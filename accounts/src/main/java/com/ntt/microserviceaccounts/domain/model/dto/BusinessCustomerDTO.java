package com.ntt.microserviceaccounts.domain.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object (DTO) representing a business customer.
 */
@Getter
@Setter
@NoArgsConstructor
public class BusinessCustomerDTO {

    private String id;
    private String documentNumber;
    private String name;
    private String customerType;
    private String address;
    private String phone;
    private String mail;
    private String representativeDocumentNumber;
    private String businessSector;
    private  String representativeName;
    private boolean active;

}
