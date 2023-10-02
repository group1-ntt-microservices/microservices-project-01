package com.ntt.microserviceaccounts.domain.model.enity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
public class Customer {


    private String id;
    private String documentNumber;
    private String name;
    private String customerType;
    private String address;
    private String phone;
    private String mail;
}
