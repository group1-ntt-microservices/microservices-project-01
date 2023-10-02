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
 * Represents a credit card entity.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "credit_cards")
public class CreditCard {
  @Id
  private String id;

  @Column(name = "customer_id")
  private String customerId;

  @Column(name = "customer_document_number")
  private String customerDocumentNumber;

  @Column(name = "customer_type")
  private String customerType;

  @Column(name = "card_number")
  private String cardNumber;

  @Column(name = "limit_amount")
  private float limitAmount;

  @Column(name = "balance_available")
  private float balanceAvailable;

  @Column(name = "balance_due")
  private float balanceDue;

  @Column(name = "is_active")
  private boolean isActive;

  @Column(name = "created_at")
  private Date createdAt;
}
