package com.ntt.microservice.customers.domain.model;

import static com.ntt.microservice.customers.configuration.Constants.TYPE_PERSONAL_CUSTOMER;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a personal customer entity.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "personal_customers")
@DiscriminatorValue(TYPE_PERSONAL_CUSTOMER)
public class PersonalCustomer extends Customer {

  @Column(name = "name")
  private String name;

  @Column(name = "last_name")
  private String lastName;

  @Column(name = "birth_date")
  private Date birthDate;

  @Column(name = "civil_status")
  private String civilStatus;
}
