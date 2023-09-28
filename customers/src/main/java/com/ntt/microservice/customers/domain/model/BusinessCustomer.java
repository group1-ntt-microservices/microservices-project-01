package com.ntt.microservice.customers.domain.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

import static com.ntt.microservice.customers.configuration.Constants.TYPE_BUSINESS_CUSTOMER;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "business_customers")
@DiscriminatorValue(TYPE_BUSINESS_CUSTOMER)
public class BusinessCustomer extends Customer {

    @Column(name = "business_name")
    private String businessName;

    @Column(name = "business_sector")
    private String businessSector;

    @Column(name = "representative_name")
    private String representativeName;

    @Column(name = "representative_document_number")
    private String representativeDocumentNumber;
}
