package com.ntt.microservice.credits.domain.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a credit entity.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "credits")
public class Credit {
  @Id
  private String id;

  @Column(name = "customer_id")
  private String customerId;

  @Column(name = "customer_document_number")
  private String customerDocumentNumber;

  @Column(name = "customer_type")
  private String customerType;

  @Column(name = "amount_granted")
  private float amountGranted;

  @Column(name = "interest")
  private float interest;

  @Column(name = "amount_interest")
  private float amountInterest;

  @Column(name = "amount_paid")
  private float amountPaid;

  @Column(name = "status")
  private int status;

  @Column(name = "is_active")
  private boolean isActive;

  @Column(name = "created_at")
  private Date createdAt;
}
