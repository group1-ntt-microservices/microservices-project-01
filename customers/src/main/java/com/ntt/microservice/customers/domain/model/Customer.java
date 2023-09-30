package com.ntt.microservice.customers.domain.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.PostLoad;
import javax.persistence.Table;
import javax.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a customer entity.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "customers")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "customer_type", discriminatorType = DiscriminatorType.STRING)
public class Customer {

  @Id
  private String id;

  @Column(name = "document_number", unique = true)
  private String documentNumber;

  @Column(name = "address")
  private String address;

  @Column(name = "mail")
  private String mail;

  @Column(name = "phone")
  private String phone;

  @Column(name = "is_active")
  private boolean isActive;

  @Column(name = "created_at")
  private Date createdAt;

  @Getter
  @Transient
  private String customerType;

  @PostLoad
  private void onLoad() {
    this.customerType = this.getClass().getAnnotation(DiscriminatorValue.class).value();
  }
}
