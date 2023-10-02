package com.ntt.microservicetransactions.domain.model.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * Represents a credit card transaction
 */
@Getter @Setter
@Document(collection = "credit_card_transactions")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreditCardTransaction {

    @Id
    private String id;

    private String type;

    private Date date;

    private float amount;

    private String atm;

    private String creditCardId;

    private String customerDocumentNumber;
}
