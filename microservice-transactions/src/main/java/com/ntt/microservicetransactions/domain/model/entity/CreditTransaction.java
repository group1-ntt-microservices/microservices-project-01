package com.ntt.microservicetransactions.domain.model.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Getter @Setter
@Document(collection = "credit_transactions")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreditTransaction {

    @Id
    private String id;

    private String type;

    private Date date;

    private float amount;

    private String atm;

    private String creditId;

    private String customerDocumentNumber;
}
