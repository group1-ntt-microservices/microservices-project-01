package com.ntt.microserviceaccounts.domain.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CustomerDTO {

    private String id;
    private String documentNumber;
    private String name;
    private String customerType;
    private String address;
    private String phone;
    private String mail;
}
