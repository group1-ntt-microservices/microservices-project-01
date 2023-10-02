package com.ntt.microservicetransactions.domain.model.entity;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * Represents a bank account transaction
 */
@Getter
@Setter
@Document(collection = "bank_account_transactions")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BankAccountTransaction {

    private String id;
    private String type;
    private Date date;
    private float amount;
    private String atm;
    private String bankAccountNumber;
    private String customerDocumentNumber;
}
